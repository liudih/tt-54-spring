package com.tomtop.ipn.services.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.tomtop.ipn.configuration.IpnSettings;
import com.tomtop.ipn.services.IBaseService;
import com.tomtop.ipn.services.IOrderStatusService;
import com.tomtop.ipn.utils.Encryption;

import entity.payment.OceanIpnResult;
import entity.payment.OceanSign;
import entity.payment.Order;
import entity.payment.SystemParameter;

@Service
public class OceanPaymentService {

	private static Logger Logger = LoggerFactory
			.getLogger(OceanPaymentService.class);

	private static final String CURRENCY_USD = "USD";

	private final static String CREDIT_3D_PAYMENT = "oceanpayment_credit_3D";

	@Autowired
	IpnSettings setting;

	@Autowired
	IOrderStatusService service;

	@Autowired
	IBaseService baseService;

	@Autowired
	Encryption encryption;

	/**
	 * 验证ocean返回数据所需的signvalue
	 *
	 * @param form
	 * @param secureCode
	 * @return
	 */
	public OceanSign getOceanValidSignValue(OceanIpnResult raw) {

		try {
			NetHttpTransport transport = new NetHttpTransport();
			// HttpContent content = new JsonHttpContent(new MockJsonFactory(),
			// raw);

			String json = JSON.toJSON(raw).toString();

			HttpContent content = new ByteArrayContent("application/json",
					json.getBytes());

			String urlString = setting.getOceanSignValueUrl();

			if (urlString == null || urlString.length() == 0) {
				throw new NullPointerException(
						"can not get config:oceanSignValueUrl");
			}
			GenericUrl url = new GenericUrl(urlString);

			HttpRequest request = transport.createRequestFactory()
					.buildPostRequest(url, content);

			String result = request.execute().parseAsString();
			return new OceanSign(null, result, null);
		} catch (IOException e) {
			Logger.error("getOceanValidSignValue error", e);
			return new OceanSign(null, null,
					"get ocean sign value by http error");
		}

	}

	/**
	 * 由于主站有肯能挂掉，所以该方法直接连接数据库
	 * 
	 * @param raw
	 * @return
	 */
	public OceanSign getOceanValidSignValueV2(OceanIpnResult raw) {
		if (raw == null) {
			Logger.info("OceanIpnResult is null");
			return null;
		}

		try {

			String orderNum = raw.getOrder_number();
			Order order = service.getOrderByOrderNum(orderNum);
			if (order == null) {
				Logger.info("can not get order:{}", orderNum);
				return new OceanSign(null, null, "not find order in database");
			}

			String ccy = order.getCcurrency();
			Double grandTotal = order.getFgrandtotal();

			String paymentId = order.getCpaymentid();

			Integer site = order.getIwebsiteid();
			if (site == null) {
				site = 1;
			}

			SystemParameter currentCcySysPara = baseService.getSysPara(
					"credit_" + ccy, site);
			if (currentCcySysPara == null) {
				String limitStr = null;
				SystemParameter usdSysPara = null;
				if (!CURRENCY_USD.equals(ccy)) {
					usdSysPara = baseService.getSysPara("credit_USD", site);
					grandTotal = baseService.exchange(grandTotal, ccy, "USD");
				}

				if (usdSysPara != null) {
					limitStr = usdSysPara.getCparametervalue();
				} else {
					limitStr = "300";
				}
				double pricelimit = Double.parseDouble(limitStr);
				// 如果大于某金额就进入信用卡3D验证支付
				if (grandTotal > pricelimit) {
					Logger.info("credit_3D_payment completed");
					paymentId = CREDIT_3D_PAYMENT;
				}
			} else {
				String limitStr = currentCcySysPara.getCparametervalue();
				double pricelimit = Double.parseDouble(limitStr);
				// 如果大于某金额就进入信用卡3D验证支付
				if (grandTotal > pricelimit) {
					Logger.info("credit_3D_payment completed");
					paymentId = CREDIT_3D_PAYMENT;
				}

				if (!CURRENCY_USD.equals(ccy)) {
					grandTotal = baseService.exchange(grandTotal, ccy, "USD");
				}

			}

			// Integer site = order.getIwebsiteid();

			String account = service.getAccount(site, grandTotal, paymentId);

			if (StringUtils.isEmpty(account)) {
				Logger.info("account is null");
				return new OceanSign(null, null, "not find account");
			}

			JSONObject accountJson = JSONObject.parseObject(account);

			StringBuilder text = new StringBuilder();
			text.append(raw.getAccount());
			text.append(raw.getTerminal());
			text.append(raw.getOrder_number());
			text.append(raw.getOrder_currency());
			text.append(raw.getOrder_amount());
			text.append(raw.getOrder_notes());
			text.append(raw.getCard_number());
			text.append(raw.getPayment_id());
			text.append(raw.getPayment_authType());
			text.append(raw.getPayment_status());
			text.append(raw.getPayment_details());
			text.append(raw.getPayment_risk());
			text.append(accountJson.getString("secureCode"));

			String secret = encryption.encodeSHA256(text.toString());
			return new OceanSign(text.toString(), secret, null);
		} catch (Exception e) {
			Logger.error("create sign occur error", e);
			return new OceanSign(null, null, e.getMessage());
		}
	}
}

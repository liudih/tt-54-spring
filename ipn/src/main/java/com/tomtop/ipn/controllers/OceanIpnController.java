package com.tomtop.ipn.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tomtop.ipn.events.EventBroker;
import com.tomtop.ipn.events.LogEvent;
import com.tomtop.ipn.events.PaymentCompletedEvent;
import com.tomtop.ipn.services.IOrderStatusService;
import com.tomtop.ipn.services.impl.OceanPaymentService;

import entity.payment.OceanIpnResult;
import entity.payment.OceanSign;

/**
 * 钱海IPN
 * 
 * @author lijun
 *
 */
@Controller
@RequestMapping(value = "/ocean", method = RequestMethod.POST)
public class OceanIpnController {

	public final static String COMPLETED = "Completed";
	public final static String CMD = "&cmd=_notify-validate";
	public final static String VERIFIED = "VERIFIED";
	public final static String REFUND = "refund";

	private static Logger Logger = LoggerFactory
			.getLogger(OceanIpnController.class);

	@Autowired
	EventBroker eventBroker;

	@Autowired
	OceanPaymentService service;

	@Autowired
	IOrderStatusService orderService;

	@Value("${oceanValidSignV2}")
	Boolean oceanValidSignV2;

	@RequestMapping("/ipn")
	@ResponseBody
	public void ipn(@RequestBody String raw, HttpServletResponse response) {
		Logger.info("ocean ipn raw:{}", raw);
		Document dom = this.parseContent(raw);

		if (dom != null) {
			OceanIpnResult result = new OceanIpnResult();

			Map<String, String> map = Maps.newHashMap();
			NodeList nodeList = dom.getElementsByTagName("response").item(0)
					.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == 1) {
					map.put(node.getNodeName(), node.getTextContent());
				}
			}

			try {
				BeanUtils.populate(result, map);
			} catch (Exception e) {
				Logger.error("BeanUtils populate map to result: ", e);
				// bad request
				response.setStatus(400);
				return;
			}

			if (null == result.getOrder_number()
					|| null == result.getSignValue()) {
				// 插入日志
				response.setStatus(400);
				return;
			}

			String orderNum = result.getOrder_number();
			String transactionId = result.getPayment_id();
			String paymentID = result.getPayment_id();
			String account = result.getAccount();

			// 发送事件记录日志
			LogEvent logEvent = new LogEvent(orderNum, raw, paymentID);
			this.eventBroker.post(logEvent);

			OceanSign sign = null;
			if (this.oceanValidSignV2) {
				sign = service.getOceanValidSignValueV2(result);
			} else {
				sign = service.getOceanValidSignValue(result);
			}

			if (sign != null
					&& sign.getSign() != null
					&& sign.getSign().toLowerCase()
							.equals(result.getSignValue().toLowerCase())) {
				// 退款
				if (result.getNotice_type() != null
						&& REFUND.equals(result.getNotice_type().toLowerCase())) {
					String pushStatus = result.getPush_status();
					try {

						// 退款成功
						if ("1".equals(pushStatus)) {
							orderService.changeStatusToPaymentRefunded(
									orderNum, transactionId, account);
						}
						response.setStatus(202);
						ServletOutputStream out;

						out = response.getOutputStream();
						out.write("receive-ok".getBytes());
						out.flush();
						return;
					} catch (Exception e) {
						Logger.error("changeStatusToPaymentRefunded:{} error",
								orderNum, e);
						response.setStatus(500);
						return;
					}
				}
				// 交易IPN
				if ("1".equals(result.getPayment_status())) {
					try {
						// 订单成功，更改订单paymentstatus状态
						orderService.changeStatusToPaymentComplete(orderNum,
								transactionId, account);
						List<String> orderNums = Lists.newLinkedList();
						orderNums.add(orderNum);
						// 发支付完成事件
						PaymentCompletedEvent event = new PaymentCompletedEvent(
								orderNums, transactionId, null, null, null);
						this.eventBroker.post(event);

						response.setStatus(202);
						ServletOutputStream out;

						out = response.getOutputStream();
						out.write("receive-ok".getBytes());
						out.flush();
						return;
					} catch (IOException e) {
						Logger.error("changeStatusToPaymentComplete:{} error",
								orderNum, e);
						response.setStatus(500);
						return;
					}

				} else if ("-1".equals(result.getPayment_status())) {
					try {
						// 订单padding中，预授权中，更改订单paymentstatus状态
						orderService.changeStatusToPaymentPending(orderNum,
								transactionId, account);
						response.setStatus(202);

						ServletOutputStream out = response.getOutputStream();
						out.write("receive-ok".getBytes());
						out.flush();
						return;
					} catch (Exception e) {
						Logger.error("changeStatusToPaymentPending:{} error",
								orderNum, e);
						response.setStatus(500);
						return;
					}
				} else if ("0".equals(result.getPayment_status())) {
					try {
						String details = result.getPayment_details();
						if (details != null && !details.startsWith("20061:")) {
							orderService.changeStatusToPaymentFailed(orderNum,
									transactionId, account);
						}
						response.setStatus(202);

						ServletOutputStream out = response.getOutputStream();
						out.write("receive-ok".getBytes());
						out.flush();
						return;
					} catch (Exception e) {
						Logger.error("changeStatusToPaymentFailed:{} error",
								orderNum, e);
						response.setStatus(500);
						return;
					}
				}
			} else {
				JSONObject json = new JSONObject();
				json.put("error", sign != null ? sign.getError() : "sign error");
				json.put("original", sign != null ? sign.getOriginal()
						: "sign is null");
				// 发送事件记录日志
				LogEvent event = new LogEvent(orderNum, json.toJSONString(),
						paymentID);
				this.eventBroker.post(event);

				Logger.error("orderNum:{} sign error", orderNum);

			}
		}

	}

	private Document parseContent(String raw) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new ByteArrayInputStream(raw.getBytes()));
		} catch (Exception e) {
			Logger.error("parse Ocean raw error", e);
			return null;
		}

	}

}

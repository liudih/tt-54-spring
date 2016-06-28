package com.tomtop.ipn.controllers;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.collect.Maps;
import com.tomtop.ipn.configuration.IpnSettings;
import com.tomtop.ipn.events.EventBroker;
import com.tomtop.ipn.events.LogEvent;
import com.tomtop.ipn.services.IOrderStatusService;
import com.tomtop.ipn.services.impl.DropShippingService;
import com.tomtop.ipn.services.impl.TotalOrderService;

@Controller
@RequestMapping(value = "/paypal", method = RequestMethod.POST)
public class PaypalIpnController {

	public final static String COMPLETED = "Completed";
	public final static String PENDING = "Pending";
	public final static String CMD = "&cmd=_notify-validate";
	public final static String VERIFIED = "VERIFIED";
	public final static String REFUNDED = "Refunded";

	private static Logger Logger = LoggerFactory
			.getLogger(PaypalIpnController.class);

	@Autowired
	EventBroker eventBroker;

	@Autowired
	IOrderStatusService service;

	@Autowired
	DropShippingService dsService;

	@Autowired
	TotalOrderService totalOrderService;

	@Autowired
	IpnSettings setting;

	@RequestMapping("/ipn")
	@ResponseBody
	public void ipn(@RequestBody String raw, HttpServletResponse response) {
		Logger.info("paypal ipn raw:{}", raw);
		Map<String, String> nvp = this.parseContent(raw);

		String orderNum = nvp.get("invoice");
		String transactionId = nvp.get("txn_id");
		String paymentStatus = nvp.get("payment_status");
		// 收款子账号，如果没有子账号，那么子账号和主账号是一样的，这个地方取子账号
		String receiverEmail = nvp.get("receiver_email");

		// 发事件记录日志
		LogEvent event = new LogEvent(orderNum, raw, transactionId);
		eventBroker.post(event);
		// 校验数据是否来自paypal
		String verified = checkIpn(raw);
		Logger.info("verified:{}", verified);
		if (verified != null && VERIFIED.equals(verified)) {

			if (orderNum != null && COMPLETED.equals(paymentStatus)) {
				// 更新订单状态

				this.service.changeStatusToPaymentComplete(orderNum,
						transactionId, receiverEmail);

				Logger.info("{} completed succeed", orderNum);

				response.setStatus(202);

			} else if (orderNum != null && PENDING.equals(paymentStatus)) {

				// 更新订单状态

				this.service.changeStatusToPaymentPending(orderNum,
						transactionId, receiverEmail);
				Logger.info("{} pending succeed", orderNum);
				response.setStatus(202);

			} else if (orderNum != null && REFUNDED.equals(paymentStatus)) {
				// 退款
				this.service.changeStatusToPaymentRefunded(orderNum,
						transactionId, receiverEmail);
				Logger.info("{} refunded succeed", orderNum);
				response.setStatus(202);
			} else {
				// 让paypal接着发消息
				response.setStatus(400);
			}
		} else {
			// 发事件记录日志
			LogEvent e = new LogEvent(orderNum, verified, transactionId);
			eventBroker.post(e);
		}
	}

	private Map<String, String> parseContent(String raw) {
		List<NameValuePair> nvp = URLEncodedUtils.parse(raw,
				Charset.forName("UTF-8"));
		Map<String, String> result = Maps.newLinkedHashMap();
		nvp.forEach(p -> {
			result.put(p.getName(), p.getValue());
		});
		return result;
	}

	/**
	 * 应答IPN去核对数据
	 * 
	 * @author lijun
	 * @return
	 */
	private String checkIpn(String raw) {
		try {
			raw = raw + CMD;
			NetHttpTransport transport = new NetHttpTransport();
			HttpContent content = new ByteArrayContent(
					"application/x-www-form-urlencoded", raw.getBytes(Charset
							.forName("UTF-8")));
			String urlStr = setting.getEndpoint();
			GenericUrl url = new GenericUrl(urlStr);

			HttpRequest request = transport.createRequestFactory()
					.buildPostRequest(url, content);
			String result = request.execute().parseAsString();
			return result;
		} catch (Exception e) {
			Logger.error("check ipn error", e);
			return null;
		}

	}
}

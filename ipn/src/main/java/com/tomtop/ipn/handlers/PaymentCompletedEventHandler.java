package com.tomtop.ipn.handlers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.eventbus.Subscribe;
import com.tomtop.ipn.configuration.IpnSettings;
import com.tomtop.ipn.events.PaymentCompletedEvent;
import com.tomtop.ipn.services.IOrderStatusService;

import entity.payment.Order;

/**
 * 记录ipn日志
 * 
 * @author lijun
 *
 */
@Service
public class PaymentCompletedEventHandler implements IEventHandler {

	private static Logger Logger = LoggerFactory
			.getLogger(PaymentCompletedEventHandler.class);

	@Autowired
	IOrderStatusService service;

	@Autowired
	IpnSettings setting;
	
	@Value("${ipn.eventUrl}")
	String eventUrl;
	
	@Value("${ipn.eventToken}")
	String eventToken;

	@Subscribe
	public void execute(PaymentCompletedEvent payload) {
		List<String> orderNums = payload.getOrderNums();
		if (orderNums == null || orderNums.size() == 0) {
			return;
		}
		
		orderNums.forEach(n -> {
			try {
				Order order = service.getOrderByOrderNum(n);
				if (order != null && order.getIid() != null) {
					service.addOrderStatusHistory(order.getIid(), 2);
				}
			} catch (Exception e) {
				Logger.error("addOrderStatusHistory:{} error",n,e);
			}
		});

//		try {
//			NetHttpTransport transport = new NetHttpTransport();
//
//			String transactionId = payload.getTransactionId();
//			// String json = JSON.toJSON(orderNums).toString();
//
//			JSONObject json = new JSONObject();
//			json.put("orderNums", orderNums);
//			json.put("transactionId", transactionId);
//
//			HttpContent content = new ByteArrayContent("application/json", json
//					.toJSONString().getBytes());
//
//			String urlString = setting.getSendEventUrl();
//
//			if (urlString == null || urlString.length() == 0) {
//				throw new NullPointerException(
//						"can not get config:sendEventUrl");
//			}
//			GenericUrl url = new GenericUrl(urlString);
//
//			HttpRequest request = transport.createRequestFactory()
//					.buildPostRequest(url, content);
//
//			request.execute();
//
//		} catch (IOException e) {
//			Logger.error("send PaymentCompletedEvent to tomtop error", e);
//		}
		
		
		//发事件
		
		try {
			NetHttpTransport transport = new NetHttpTransport();

			String onumber = orderNums.get(0);
			Order oneOrder = service.getOrderByOrderNum(onumber);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code", "EVENT_CODE_ORDER_COMPLETE");
			JSONObject param = new JSONObject();
			param.put("email", oneOrder.getCmemberemail());
			param.put("website", oneOrder.getIwebsiteid());
			param.put("currency", oneOrder.getCcurrency());
			param.put("orderNumber", oneOrder.getCordernumber());
			param.put("money", oneOrder.getFgrandtotal());
			jsonObj.put("param", param);
			
			HttpContent content = new ByteArrayContent("application/json", jsonObj
					.toJSONString().getBytes());

			String urlString = eventUrl;
			if (urlString == null || urlString.length() == 0) {
				throw new NullPointerException(
						"can not get config:sendEventUrl");
			}
			GenericUrl url = new GenericUrl(urlString);

			HttpRequest request = transport.createRequestFactory()
					.buildPostRequest(url, content);
			HttpHeaders headers = new HttpHeaders();
			headers.set("token", eventToken);
			request.setHeaders(headers);
			request.execute();

		} catch (IOException e) {
			Logger.error("send PaymentCompletedEvent to tomtop error", e);
		}
	}
}

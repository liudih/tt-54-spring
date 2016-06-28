package com.tomtop.ipn.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.tomtop.ipn.configuration.IpnSettings;
import com.tomtop.ipn.events.EventBroker;
import com.tomtop.ipn.events.PaymentCompletedEvent;
import com.tomtop.ipn.services.IOrderStatusService;
import com.tomtop.ipn.services.impl.DropShippingService;
import com.tomtop.ipn.services.impl.TotalOrderService;

@Controller
@RequestMapping(value = "/kount", method = RequestMethod.POST)
public class KountController {

	public final static String COMPLETED = "Completed";
	public final static String PENDING = "Pending";
	public final static String CMD = "&cmd=_notify-validate";
	public final static String VERIFIED = "VERIFIED";

	private static Logger Logger = LoggerFactory
			.getLogger(KountController.class);

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

	@Autowired
	IOrderStatusService orderService;

	@RequestMapping()
	@ResponseBody
	public void ipn(@RequestBody String raw, HttpServletResponse response) {
		Logger.info("kount ipn raw:{}", raw);
		try {
			Document doc = (Document) DocumentHelper.parseText(raw);
			Element books = doc.getRootElement();
			List<Element> nodes = books.elements("event");
			for (Element e : nodes) {
				String name = e.element("name").getText();
				if ("WORKFLOW_STATUS_EDIT".equals(name)) {
					Element key = e.element("key");
					String orderNum = key.attributeValue("order_number");
					String site = key.attributeValue("site");
					String transactionId = key.getText();
					String old_value = e.element("old_value").getText();
					String new_value = e.element("new_value").getText();
					String occurred = e.element("occurred").getText();
					// "R".equals(old_value)
					if (!"A".equals(old_value) && "A".equals(new_value)) {
						// 订单成功，更改订单paymentstatus状态
						// recode 收款账号没取到
						orderService.changeStatusToPaymentComplete(orderNum,
								transactionId, null);
						List<String> orderNums = Lists.newLinkedList();
						orderNums.add(orderNum);
						// 发支付完成事件
						PaymentCompletedEvent event = new PaymentCompletedEvent(
								orderNums, transactionId, null, null, null);
						this.eventBroker.post(event);
					} else if ("E".equals(new_value)) {

					} else if ("D".equals(new_value)) {

					} else if ("B".equals(new_value)) {

					}
				}
			}
			// Iterator users_subElements =
			// books.elementIterator("UID");//指定获取那个元素
		} catch (DocumentException e) {
			Logger.error("kount-DocumentException:{}", e.toString());
		}

	}

}

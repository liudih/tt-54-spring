package com.tomtop.management.base.controllers;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.WebUtil;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Currency;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.CurrencyObject;
import com.tomtop.management.services.BaseParameterService;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.CurrencyService;

import freemarker.log.Logger;

/**
 * 
 * @ClassName: CurrencyManageController
 * @Description: TODO(汇率管理控制类)
 * @author Guozy
 * @date 2015年12月22日
 *
 */
@Controller
@RequestMapping("/base/currency")
public class CurrencyManageController {

	@Autowired
	CurrencyService currencyService;
	@Autowired
	CommonService commonService;
	@Autowired
	BaseParameterService baseParameterService;
	@Autowired
	private EventBus eventBus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<BaseParameter> parameters = baseParameterService.getAllParamByCurrency();
		model.put("parameters", parameters);
		model.put("section", "baseModul/currencyManage.ftl");
		return "index";
	}

	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Page<CurrencyObject> getList(int pageNo, int pageLimit, Currency currency) {
		Page<CurrencyObject> currencyPage = currencyService.getcurrencyPage(pageNo, pageLimit, currency);
		return currencyPage;
	}

	@RequestMapping(value = "/validateName", method = RequestMethod.POST)
	@ResponseBody
	public String validateName(String name) {
		String msg = null;
		List<Currency> layouts = currencyService.validateName(name);
		if (layouts.size() > 0) {
			msg = "FAIL";
		} else {
			msg = "SUCCESS";
		}
		return msg;
	};

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute Currency currency) {
		String msg = null;
		try {
			currencyService.addCurrency(currency);
			eventBus.post(new BaseCacheEvent("baseCurrency", EventType.Add));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute Currency currency) {
		String msg = null;
		try {
			currencyService.updateCurrency(currency);
			eventBus.post(new BaseCacheEvent("baseCurrency", EventType.Update));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	@RequestMapping(value = "/getId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Currency getId(@PathVariable(value = "id") int id) {
		return currencyService.getCurrencyId(id);
	};

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "delIds") String delIds) {
		String msg = null;
		int deleteid = currencyService.delById(delIds);
		if (deleteid > 0) {
			eventBus.post(new BaseCacheEvent("baseCurrency", EventType.Delete));
			msg = "SUCCESS";
		} else {
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: getsynchroList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2015年12月23日
	 */
	@RequestMapping(value = "/getSynchroList", method = RequestMethod.GET)
	@ResponseBody
	public String getSynchroList() {
		String msg;
		List<Currency> currencies=commonService.getAllCurrency();
		String url = "http://download.finance.yahoo.com/d/quotes.csv";
		try {
			for (Currency currency : currencies) {
				String param = "e=" + URLEncoder.encode(".csv") + "&f=l1&s=" + URLEncoder.encode("USD" + currency.getCode()+ "=x");
				String s = WebUtil.executeGet(url + "?" + param);
				Currency cur=new Currency();
				cur.setId(currency.getId());
				cur.setNew_rate(Double.parseDouble(s));
				cur.setSynchro_date(new Timestamp(new Date().getTime()));
				Currency.db().update(cur);
			}
			msg="SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg=null;
		}
		return msg;
	};
}

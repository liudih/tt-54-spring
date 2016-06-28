package com.tomtop.management.base.controllers;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.service.model.LayoutObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.LayoutService;

/**
 * 
 * @ClassName: LayoutManageController
 * @Description: TODO(布局控制类)
 * @author Guozy
 * @date 2015年12月16日
 *
 */
@Controller
@RequestMapping("/base/layout")
public class LayoutManageController {
	private static Logger log = Logger.getLogger(LayoutManageController.class.getName());
	@Autowired
	LayoutService layoutService;
	@Autowired
	CommonService commonService;

	@Autowired
	private EventBus eventBus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("section", "baseModul/layoutManage.ftl");
		return "index";
	}

	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Page<LayoutObject> getList(int pageNo, int pageLimit, Layout layout, String clients,
			String languages) {
		layout.setName(layout.getName() == "" ? null : layout.getName());
		Page<LayoutObject> mcPage = layoutService.getLayoutPage(pageNo, pageLimit, layout, clients,languages);
		return mcPage;
	}


	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute Layout layout, String clients) {
		String msg = null;
		try {
			layoutService.addLayout(layout, clients);
			eventBus.post(new ProductCacheEvent("base_layout", EventType.Add));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute Layout layout) {
		String msg = null;
		try {
			Layout.db().update(layout);
			eventBus.post(new ProductCacheEvent("base_layout", EventType.Update));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	@RequestMapping(value = "/getId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Layout getId(@PathVariable(value = "id") int id) {
		return layoutService.getLayoutId(id);
	};

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "delIds") String delIds) {
		String msg = null;
		int deleteid = layoutService.delById(delIds);
		if (deleteid > 0) {
			eventBus.post(new ProductCacheEvent("base_layout", EventType.Delete));
			msg = "SUCCESS";
		} else {
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: validateCode
	 * @Description: TODO(验证布局标识唯一性)
	 * @param @param
	 *            code
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月18日
	 */
	@RequestMapping(value = "/validatecode", method = RequestMethod.POST)
	@ResponseBody
	public String validateCode(Layout layout, String clients) {
		String msg;
		List<Layout> layouts = layoutService.validateCode(layout, clients);
		if (layouts.size() <= 0) {
			msg = "SUCCESS";
		} else {
			msg = "FAIL";
		}
		return msg;
	};
	
	@RequestMapping(value = "/validateLayout", method = RequestMethod.POST)
	@ResponseBody
	public String validateLayout(Layout layout, String clients) {
		String msg = null;
		List<Layout> layouts = layoutService.validateLayout(layout, clients);
		if (layouts.size() > 0) {
			msg = "FAIL";
		} else {
			msg = "SUCCESS";
		}
		return msg;
	};
}

package com.tomtop.management.base.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.manage.model.LoyoutModul;
import com.tomtop.management.service.model.LayoutModelObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.LayoutEnquiryService;

public class LayoutModulesManageController {

	@Autowired
	LayoutEnquiryService enquiryService;

	@Autowired
	CommonService commonService;

	/**
	 * 进入布局模块页面
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<LoyoutModul> loyoutModulList = commonService.getAllLoyoutModuls();
		List<BaseParameter> parameters = commonService.getAllParam();
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("loyoutModulList", loyoutModulList);
		model.put("paramters", parameters);
		model.put("section", "baseModul/modules_context_manage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getModuleCount
	 * @Description: 查询信息 可以根据条件
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            modul
	 * @param @return
	 * @param @throws
	 *            IllegalAccessException
	 * @param @throws
	 *            InvocationTargetException 参数
	 * @return Page<LayoutObject> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月16日
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Page<LayoutModelObject> getModuleCount(int pageNo, int pageLimit, LoyoutModul modul) {
		Page<LayoutModelObject> lPage = enquiryService.getLoyoutModulPage(pageNo, pageLimit, modul);
		return lPage;
	}

	/**
	 * 
	 * @Title: addLoyout
	 * @Description: 添加布局模块信息
	 * @param @param
	 *            modul
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月16日
	 */
	@RequestMapping("/add")
	@ResponseBody
	public boolean addLoyout(LoyoutModul modul) {
		int addR = enquiryService.getAddReturn(modul);
		boolean result = true;
		if (addR <= 0) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @Title: getLoyoutModulById
	 * @Description: 根据主键查询信息
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return LoyoutModul 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LoyoutModul getById(@PathVariable(value = "id") int id) {
		LoyoutModul m = LoyoutModul.db().find(LoyoutModul.class, id);
		return m;
	}

	/**
	 * 
	 * @Title: updateModulById
	 * @Description: 根据主键修改信息
	 * @param @param
	 *            m
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月16日
	 */
	@RequestMapping("/update")
	@ResponseBody
	public boolean updateById(LoyoutModul m) {
		int re = enquiryService.getUpdateReturn(m);
		boolean result = true;
		if (re <= 0) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @Title: deleteByIds
	 * @Description: 根据主键删除信息
	 * @param @param
	 *            delIds
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteByIds(@PathVariable(value ="delIds") String delIds) {
		LoyoutModul modul = null;
		int delCount = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			modul = LoyoutModul.db().find(LoyoutModul.class,
					Integer.parseInt(ids[i]));
			modul.setIs_deleted(1);
			LoyoutModul.db().update(modul);
			delCount++;
		}
		if (delCount > 0) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @Title: checkName
	 * @Description: 检查模块名称是否重复
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkName(@RequestParam("name") String name) {
		boolean re = true;
		List<LoyoutModul> list = enquiryService.checkModuleName(name);
		if (list.size() > 0) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}
}

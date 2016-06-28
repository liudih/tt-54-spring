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

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.LanguageObject;
import com.tomtop.management.services.LanguageService;

@Controller
@RequestMapping("/base/language")
public class LanguageController {

	@Autowired
	LanguageService languageService;
	
	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: 进入界面
	 * @param @param
	 *            model
	 * @param @param
	 *            section
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		model.put("section", "baseModul/language_manage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getCliPage
	 * @Description: 查询
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            language
	 * @param @return
	 *            参数
	 * @return Page<LanguageObject> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月21日
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Page<LanguageObject> getCliPage(int pageNo, int pageLimit, Language language) {
		Page<LanguageObject> pagel = languageService.getLanguagePage(pageNo, pageLimit, language);
		return pagel;
	}

	/**
	 * 
	 * @Title: add
	 * @Description:添加
	 * @param @param
	 *            language
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月21日
	 */
	@RequestMapping("/add")
	@ResponseBody
	public boolean add(Language language) {
		boolean result = true;
		int count = languageService.addLanguage(language);
		if (count <= 0) {
			result = false;
		}else{
			eventBus.post(new BaseCacheEvent("baseLangage", EventType.Add));
		}
		return result;
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改
	 * @param @param
	 *            language
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月21日
	 */
	@RequestMapping("/update")
	@ResponseBody
	public boolean update(Language language) {
		boolean result = true;
		int count = languageService.updateLanguage(language);
		if (count <= 0) {
			result = false;
		}else{
			eventBus.post(new BaseCacheEvent("baseLangage", EventType.Update));
		}
		return result;
	}

	/**
	 * 
	 * @Title: getById
	 * @Description: 根据主键查询信息
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return Language 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Language getById(@PathVariable(value = "id") int id) {
		return Language.db().find(Language.class, id);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 根据主键删除一条或多条信息
	 * @param @param
	 *            delIds
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean delete(@PathVariable(value = "delIds") String delIds) {
		Language lan = null;
		int count = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			lan = Language.db().find(Language.class, ids[i]);
			lan.setIs_deleted(1);
			Language.db().update(lan);
			count++;
		}
		if (count > 0) {
			eventBus.post(new BaseCacheEvent("baseLangage", EventType.Delete));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @Title: checkLanguageName
	 * @Description: 验证语言名称是否存在
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkLanguageName(@RequestParam("id") Integer id,@RequestParam("name") String name) {
		boolean re = true;
		int sList = languageService.checkName(id,name);
		if (sList > 0) {
			re = false;
		}
		return re;
	}

	/**
	 * 
	 * @Title: checkLanguageCode
	 * @Description: 验证语言标识是否存在
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/validateC", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkLanguageCode(@RequestParam("id") Integer id,@RequestParam("name") String name) {
		boolean re = true;
		int sList = languageService.checkCode(id,name);
		if (sList > 0) {
			re = false;
		}
		return re;
	}
}

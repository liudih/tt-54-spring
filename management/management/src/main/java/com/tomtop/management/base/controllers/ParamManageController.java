package com.tomtop.management.base.controllers;

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
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.BaseParameterObject;
import com.tomtop.management.services.BaseParameterService;
import com.tomtop.management.services.CommonService;

/**
 * 
 * @ClassName: ParamManageController
 * @Description: TODO(参数管理控制类)
 * @author Guozy
 * @date 2015年12月16日
 *
 */
@Controller
@RequestMapping("/base/param")
public class ParamManageController {

	@Autowired
	BaseParameterService baseParameterService;

	@Autowired
	CommonService commonService;

	@Autowired
	private EventBus eventBus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("section", "baseModul/paramManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getBaseParametersPage
	 * @Description: TODO(根据查找条件，获取参数的数据信息)
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            baseParameter
	 * @param @return
	 *            参数
	 * @return Page<BaseParameterModel> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Page<BaseParameterObject> getList(int pageNo, int pageLimit, BaseParameter baseParameter, String clients,
			String languages) {
		baseParameter.setType(baseParameter.getType() == "" ? null : baseParameter.getType());
		Page<BaseParameterObject> paramPage = baseParameterService.getBaseParametersPage(pageNo, pageLimit,
				baseParameter, clients, languages);
		return paramPage;
	}

	/**
	 * 
	 * @Title: addBaseParameter
	 * @Description: TODO(添加参数的数据信息)
	 * @param @param
	 *            baseParameter
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute BaseParameter baseParameter) {
		String msg = null;
		try {
			Layout.db().save(baseParameter);
			eventBus.post(new BaseCacheEvent("parameter", EventType.Add));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: updateBaseParameter
	 * @Description: TODO(根据相应条件，获取参数的信息)
	 * @param @param
	 *            baseParameter
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute BaseParameter baseParameter) {
		String msg = null;
		try {
			Layout.db().update(baseParameter);
			eventBus.post(new BaseCacheEvent("parameter", EventType.Update));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: getBaseParameterById
	 * @Description: TODO(通过参数编号，获取参数的信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return BaseParameter 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/getId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseParameter getId(@PathVariable(value = "id") int id) {
		return baseParameterService.getBaseParameterByid(id);
	};

	/**
	 * 
	 * @Title: delBaseParameterById
	 * @Description: TODO(通过编号，删除相应的信息)
	 * @param @param
	 *            delIds
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "delIds") String delIds) {
		String msg = null;
		int deleteid = baseParameterService.delBaseParameterById(delIds);
		if (deleteid > 0) {
			eventBus.post(new BaseCacheEvent("parameter", EventType.Delete));
			msg = "SUCCESS";
		} else {
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: validateParams
	 * @Description: TODO(验证数据信息)
	 * @param @param
	 *            baseParameter
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2015年12月17日
	 */
	@RequestMapping(value = "/validateParams", method = RequestMethod.POST)
	@ResponseBody
	public String validateParams(BaseParameter baseParam) {
		String msg = null;
		List<BaseParameter> paseParameters = baseParameterService.validateParams(baseParam);
		if (paseParameters.size() > 0) {
			msg = "FAIL";
		} else {
			msg = "SUCCESS";
		}
		return msg;
	};
}

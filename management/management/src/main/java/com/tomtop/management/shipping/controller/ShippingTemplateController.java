package com.tomtop.management.shipping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.shipping.model.ShippingTemplate;
import com.tomtop.management.shipping.service.ShippingTemplateService;
import com.tomtop.management.shipping.values.ShippingTemplateValue;
import com.tomtop.management.shipping.values.ValidCheckInfo;

/**
 * 
 * @ClassName: ShippingTemplateController
 * @Description: TODO(物流模板定义管理控制类)
 * @author Guozy
 * @date 2016年2月19日
 *
 */
@Controller
@RequestMapping("/shipping/template")
public class ShippingTemplateController {

	@Autowired
	private ShippingTemplateService shippingTemplateService;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            model
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		model.put("section", "shipping/shippingTemplateManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getShippingTemplatePage
	 * @Description: TODO(分页查询物流模块定义)
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            shippingTemplate
	 * @param @return
	 *            参数
	 * @return Page<ShippingTemplateObject> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Page<ShippingTemplateValue> getShippingTemplatePage(int pageNo, int pageLimit,
			ShippingTemplate shippingTemplate) {
		return shippingTemplateService.getShippingTemplatePage(pageNo, pageLimit, shippingTemplate);
	}

	/**
	 * 
	 * @Title: add
	 * @Description: TODO(新郑物流模块定义信息)
	 * @param @param
	 *            template
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(ShippingTemplate template) {
		int addCount = shippingTemplateService.addShippingTemplate(template);
		if (addCount > 0) {
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: update
	 * @Description: TODO(根据物流模块定义编号，修改物流模块信息)
	 * @param @param
	 *            template
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(ShippingTemplate template) {
		int updateCount = shippingTemplateService.updateShippingTemplate(template);
		if (updateCount > 0) {
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO(根据物流模块定义编号，删除物流模块信息)
	 * @param @param
	 *            delIds
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.GET)
	@ResponseBody
	public ValidCheckInfo delete(@PathVariable(value = "delIds") String delIds) {
		return shippingTemplateService.deleteShippingTemplate(delIds);
	}

	/**
	 * 
	 * @Title: validateName
	 * @Description: TODO(模块名称不可以重复)
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	@RequestMapping(value = "/validateName", method = RequestMethod.POST)
	@ResponseBody
	public String validateName(String template_name) {
		String msg = null;
		List<ShippingTemplate> shippingTemplates = shippingTemplateService.validateName(template_name);
		if (shippingTemplates.size() > 0) {
			msg = "fail";
		} else {
			msg = "success";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: getId
	 * @Description: TODO(通过模板定义编号，获取物流模块定义信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return ShippingTemplate 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	@RequestMapping(value = "/getId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ShippingTemplate getId(@PathVariable(value = "id") int id) {
		return shippingTemplateService.getShippingTemplateById(id);
	};
}

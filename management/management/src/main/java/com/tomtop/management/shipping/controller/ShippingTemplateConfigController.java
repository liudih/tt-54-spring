package com.tomtop.management.shipping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.base.model.Storage;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.shipping.model.ShippingTemplate;
import com.tomtop.management.ebean.shipping.model.ShippingTemplateConfig;
import com.tomtop.management.ebean.shipping.model.ShippingType;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.shipping.service.ShippingTemplateConfigService;
import com.tomtop.management.shipping.values.ShippingTemplateConfigValue;
import com.tomtop.management.shipping.values.ValidCheckInfo;

@Controller
@RequestMapping("/shipping/templateConfig")
public class ShippingTemplateConfigController {
	@Autowired
	ShippingTemplateConfigService stcService;

	@Autowired
	CommonService commonService;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(物流模版配置页面显示)
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Model model) {
		List<BaseParameter> filterList = commonService.getAllFilter();
		List<Storage> storageList = commonService.getAllStorage();
		List<ShippingTemplate> shippingTemplateList = commonService.getAllShippingTemplate();
		List<ShippingType> shippingTypeList = commonService.getAllShippingType();
		model.addAttribute("filterList", filterList);
		model.addAttribute("storageList", storageList);
		model.addAttribute("shippingTemplateList", shippingTemplateList);
		model.addAttribute("shippingTypeList", shippingTypeList);
		model.addAttribute("section", "shipping/shippingTemplateConfig.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getSTCPage
	 * @Description: TODO(分页查询物流模版配置)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param stc
	 * @param @return    
	 * @return Page<ShippingTemplateConfigValue>    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<ShippingTemplateConfigValue> getSTCPage(int pageNo, int pageLimit, ShippingTemplateConfig stc) {
		return stcService.getSTCPage(pageNo, pageLimit, stc);
	}

	/**
	 * 
	 * @Title: addShippingTemplateConfig
	 * @Description: TODO(新增物流模版配置)
	 * @param @param stc
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ValidCheckInfo addShippingTemplateConfig(ShippingTemplateConfig stc) {
		ValidCheckInfo vci = stcService.addShippingTemplateConfig(stc);
		return vci;
	}

	/**
	 * 
	 * @Title: getSTCById
	 * @Description: TODO(通过id查询物流模版配置)
	 * @param @param id
	 * @param @return    
	 * @return ShippingTemplateConfig    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ShippingTemplateConfig getSTCById(@PathVariable(value = "id") Integer id) {
		return stcService.getSTCById(id);
	}

	/**
	 * 
	 * @Title: updateShippingTemplateConfig
	 * @Description: TODO(修改物流模版配置)
	 * @param @param stc
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ValidCheckInfo updateShippingTemplateConfig(ShippingTemplateConfig stc) {
		ValidCheckInfo vci = stcService.updateShippingTemplateConfig(stc);
		return vci;
	}

	/**
	 * 
	 * @Title: deleteShippingTemplateConfig
	 * @Description: TODO(删除物流模版配置)
	 * @param @param Ids
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "/delete/{Ids}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteShippingTemplateConfig(@PathVariable(value = "Ids") String Ids) {
		int deleteCount = stcService.deleteShippingTemplateConfig(Ids);
		if (deleteCount > 0) {
			// eventBus.post(new BaseCacheEvent("user", EventType.Delete));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: STCUniqueValidate
	 * @Description: TODO(物流模版配置唯一检验)
	 * @param @param stc
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "/shippingTemplateConfigUV", method = RequestMethod.POST)
	@ResponseBody
	public boolean STCUniqueValidate(ShippingTemplateConfig stc) {
		return stcService.STCUniqueValidate(stc);
	}
}

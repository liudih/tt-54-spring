package com.tomtop.management.shipping.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.shipping.model.ShippingDescription;
import com.tomtop.management.ebean.shipping.model.ShippingType;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.shipping.service.ShippingTypeService;
import com.tomtop.management.shipping.values.ShippingDesValue;
import com.tomtop.management.shipping.values.ShippingTypeValue;
import com.tomtop.management.shipping.values.ValidCheckInfo;

@Controller
@RequestMapping("/shipping/type")
public class ShippingTypeController {

	@Autowired
	ShippingTypeService shippingTypeService;

	@Autowired
	CommonService commonService;

	/**
	 * 
	 * @Title: view
	 * @Description: 物流类型管理
	 * @param @param
	 *            model
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Language> languageList = commonService.getAllLanguage();
		model.put("languageList", languageList);
		model.put("type", "add");
		model.put("section", "shipping/shipping_type.ftl");
		return "index";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Page<ShippingTypeValue> getShippingPage(int pageNo, int pageLimit, ShippingType shippingType) {
		Page<ShippingTypeValue> shipping = shippingTypeService.getShipPage(pageNo, pageLimit, shippingType);
		return shipping;
	}

	@RequestMapping(value = "/operation/{id}", method = RequestMethod.GET)
	public String view(@PathVariable(value = "id") String id, Map<String, Object> model) {
		List<Language> languageList = commonService.getAllLanguage();
		model.put("languageList", languageList);
		model.put("id", id);
		model.put("type", "add");
		model.put("section", "shipping/operation_shipping_type.ftl");
		return "index";
	}

	@RequestMapping("/add")
	@ResponseBody
	public ValidCheckInfo add(ShippingTypeValue shipValue) {
		return shippingTypeService.addType(shipValue);
	}

	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		List<Language> languageList = commonService.getAllLanguage();

		model.put("languageList", languageList);
		model.put("id", id);
		model.put("type", "update");
		model.put("section", "shipping/operation_shipping_type.ftl");
		return "index";
	}

	@RequestMapping(value = "/getTypeByid/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ShippingTypeValue getById(@PathVariable(value = "id") Long id) {
		ShippingTypeValue sTypeValue = new ShippingTypeValue();
		ShippingType shippingType = ShippingType.db().find(ShippingType.class, id);
		List<ShippingDescription> shippingDescriptions = ShippingDescription.db().find(ShippingDescription.class)
				.where().eq("shipping_type_id", id.intValue()).findList();
		List<ShippingDesValue> shippingDesValues = new ArrayList<ShippingDesValue>();
		for (ShippingDescription des : shippingDescriptions) {
			ShippingDesValue sDesValue = new ShippingDesValue();
			sDesValue.setDescription(des.getDescription());
			sDesValue.setDisplay_name(des.getDisplay_name());
			sDesValue.setLanguage_id(des.getLanguage_id());
			sDesValue.setShipping_type_id(des.getShipping_type_id());
			shippingDesValues.add(sDesValue);
		}
		sTypeValue.setSdList(shippingDesValues);
		sTypeValue.setType_name(shippingType.getType_name());
		sTypeValue.setIs_enabled(shippingType.getIs_enabled());
		sTypeValue.setShipping_code(shippingType.getShipping_code());
		sTypeValue.setShipping_sequence(shippingType.getShipping_sequence());
		sTypeValue.setId(id);
		return sTypeValue;
	}

	@RequestMapping("/update")
	@ResponseBody
	public ValidCheckInfo update(ShippingTypeValue shipValue) {
		return shippingTypeService.updateType(shipValue);
	}

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteByIds(@PathVariable(value = "delIds") String delIds) {
		ShippingType shippingType = null;
		int delCount = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			shippingType = ShippingType.db().find(ShippingType.class, Integer.parseInt(ids[i]));
			shippingType.setIs_deleted(1);
			ShippingType.db().update(shippingType);
			delCount++;
		}
		if (delCount > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkName(@RequestBody ShippingTypeValue shipValue) {
		int result = shippingTypeService.checkTypeName(shipValue.getId(),shipValue.getType_name());
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
}

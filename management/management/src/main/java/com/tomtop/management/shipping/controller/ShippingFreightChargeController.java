package com.tomtop.management.shipping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.base.model.Storage;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.shipping.model.ShippingType;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.shipping.service.ShippingFreightChargeService;
import com.tomtop.management.shipping.values.ShippingFreight;

/**
 * 
 * @ClassName: ShippingFreightChargeController
 * @Description: TODO(物流运费计算管理控制类)
 * @author Guozy
 * @date 2016年2月20日
 *
 */
@Controller
@RequestMapping("/shipping/freightCharge")
public class ShippingFreightChargeController {

	@Autowired
	private CommonService commonService;
	@Autowired
	ShippingFreightChargeService sfcService;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(初始化物流运费计算信息)
	 * @param @param
	 *            model
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月20日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Country> countries = commonService.getAllCountry();
		List<ShippingType> shippingTypes = commonService.getAllShippingType();
		List<Storage> storages=commonService.getAllStorage();
		model.put("countries", countries);
		model.put("shippingTypes", shippingTypes);
		model.put("storages", storages);
		model.put("section", "shipping/shippingFreightChargeManage.ftl");
		return "index";
	}
	
	/**
	 * 
	 * @Title: freightCalculate
	 * @Description: TODO(运费计算)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param sf
	 * @param @param sort
	 * @param @return    
	 * @return Page<ShippingFreight>    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月12日
	 */
	@RequestMapping(value = "/freightCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Page<ShippingFreight> freightCalculate(int pageNo, int pageLimit, ShippingFreight sf, String sort) {
		return sfcService.freightCalculate(pageNo, pageLimit, sf, sort);
	}

}

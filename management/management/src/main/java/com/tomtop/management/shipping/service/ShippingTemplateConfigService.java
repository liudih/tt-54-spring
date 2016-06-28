package com.tomtop.management.shipping.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.base.model.Storage;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.shipping.model.ShippingTemplate;
import com.tomtop.management.ebean.shipping.model.ShippingTemplateConfig;
import com.tomtop.management.ebean.shipping.model.ShippingType;
import com.tomtop.management.shipping.values.ShippingTemplateConfigValue;
import com.tomtop.management.shipping.values.ValidCheckInfo;

@Service
public class ShippingTemplateConfigService {

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
	public Page<ShippingTemplateConfigValue> getSTCPage(int pageNo, int pageLimit, ShippingTemplateConfig stc) {
		Page<ShippingTemplateConfigValue> stcoPage = new Page<ShippingTemplateConfigValue>();
		String q = "find ShippingTemplateConfig where is_deleted = 0";
		if (null != stc.getShipping_template_id()) {
			q += " and shipping_template_id = " + stc.getShipping_template_id();
		}
		if (null != stc.getShipping_type_id()) {
			q += " and shipping_type_id = " + stc.getShipping_type_id();
		}
		if (StringUtil.isNotEmpty(stc.getCountry())) {
			q += " and country like '%" + stc.getCountry() + "%'";
		}
		if (null != stc.getFilter_id()) {
			q += " and filter_id = " + stc.getFilter_id();
		}
		if (null != stc.getIs_freeshipping()) {
			q += " and is_freeshipping = " + stc.getIs_freeshipping();
		}
		if (null != stc.getIs_especial()) {
			q += " and is_especial = " + stc.getIs_especial();
		}
		PagedList<ShippingTemplateConfig> stcPageList = ShippingTemplateConfig.db()
				.createQuery(ShippingTemplateConfig.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		stcPageList.loadRowCount();
		stcoPage.setCount(stcPageList.getTotalRowCount());
		stcoPage.setLimit(pageLimit);
		stcoPage.setPageNo(pageNo);
		List<ShippingTemplateConfigValue> stcoList = new ArrayList<ShippingTemplateConfigValue>();
		for (ShippingTemplateConfig shippingTemplateConfig : stcPageList.getList()) {
			ShippingTemplateConfigValue stco = new ShippingTemplateConfigValue();
			try {
				BeanUtils.copyProperties(stco, shippingTemplateConfig);
			} catch (Exception e) {
				e.printStackTrace();
			}
			stcoList.add(stco);
		}
		for (ShippingTemplateConfigValue stco : stcoList) {
			stco.setShippingTemplate(
					ShippingTemplate.db().find(ShippingTemplate.class, stco.getShipping_template_id()));
			stco.setShippingType(ShippingType.db().find(ShippingType.class, stco.getShipping_type_id()));
			stco.setFilter(BaseParameter.db().find(BaseParameter.class, stco.getFilter_id()));
			stco.setStorage(Storage.db().find(Storage.class, stco.getWarehouse_id()));
			stco.setCrateTime(DateUtil.dateFormat(stco.getWhenCreated()));
			stco.setUpdateTime(DateUtil.dateFormat(stco.getWhenModified()));
			if (stco.getIs_enabled() != null) {
				if (stco.getIs_enabled() == 1) {
					stco.setIsEnabled("启用");
				} else if (stco.getIs_enabled() == 0) {
					stco.setIsEnabled("禁用");
				} else {
					stco.setIsEnabled("");
				}
			} else {
				stco.setIsEnabled("");
			}
			stco.setAmountLimit(stco.getStart_amount()+"--"+stco.getAmount_limit());
			stco.setWeightLimit(stco.getStart_weight()+"--"+stco.getWeight_limit());
		}
		stcoPage.setList(stcoList);
		return stcoPage;
	}

	/**
	 * 
	 * @Title: addShippingTemplateConfig
	 * @Description: TODO(新增物流模版配置)
	 * @param @param stc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	public ValidCheckInfo addShippingTemplateConfig(ShippingTemplateConfig stc) {
		ValidCheckInfo vci = new ValidCheckInfo();
		vci = validateCountryAndShippingCode(stc);
		if(vci.getStatus() == 1){
			return vci;
		}
		stc.setIs_deleted(0);
		if(null == stc.getStart_amount()){
			stc.setStart_amount(0.0);
		}
		if(null == stc.getAmount_limit()){
			stc.setAmount_limit(0.0);
		}
		if(null == stc.getStart_weight()){
			stc.setStart_weight(0.0);
		}
		if(null == stc.getWeight_limit()){
			stc.setWeight_limit(0.0);
		}
		if(null == stc.getExtra_charge()){
			stc.setExtra_charge(0.0);
		}
		if (null == stc.getCountry_add_type()) {
			stc.setCountry_add_type(0);
		}
		if (StringUtil.isNotEmpty(stc.getCountry())) {
			stc.setCountry(stc.getCountry().toUpperCase());
		}
		if (StringUtil.isNotEmpty(stc.getPriority_shipping_code())) {
			stc.setPriority_shipping_code(stc.getPriority_shipping_code().toUpperCase());
		}
		try {
			ShippingTemplateConfig.db().insert(stc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vci.setStatus(0);
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
	public ShippingTemplateConfig getSTCById(Integer id) {
		return ShippingTemplateConfig.db().find(ShippingTemplateConfig.class, id);
	}

	/**
	 * 
	 * @Title: updateShippingTemplateConfig
	 * @Description: TODO(修改物流模版配置)
	 * @param @param stc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	public ValidCheckInfo updateShippingTemplateConfig(ShippingTemplateConfig stc) {
		ValidCheckInfo vci = new ValidCheckInfo();
		vci = validateCountryAndShippingCode(stc);
		if(vci.getStatus() == 1){
			return vci;
		}
		if(null == stc.getStart_amount()){
			stc.setStart_amount(0.0);
		}
		if(null == stc.getAmount_limit()){
			stc.setAmount_limit(0.0);
		}
		if(null == stc.getStart_weight()){
			stc.setStart_weight(0.0);
		}
		if(null == stc.getWeight_limit()){
			stc.setWeight_limit(0.0);
		}
		if (null == stc.getCountry_add_type()) {
			stc.setCountry_add_type(0);
		}
		if (null == stc.getCountry_add_type()) {
			stc.setCountry_add_type(0);
		}
		if(null == stc.getExtra_charge()){
			stc.setExtra_charge(0.0);
		}
		if (StringUtil.isNotEmpty(stc.getCountry())) {
			stc.setCountry(stc.getCountry().toUpperCase());
		}
		if (StringUtil.isNotEmpty(stc.getPriority_shipping_code())) {
			stc.setPriority_shipping_code(stc.getPriority_shipping_code().toUpperCase());
		}
		try {
			ShippingTemplateConfig.db().update(stc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vci.setStatus(0);
		return vci;
	}

	/**
	 * 
	 * @Title: deleteShippingTemplateConfig
	 * @Description: TODO(删除物流模版配置)
	 * @param @param stcids
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	public int deleteShippingTemplateConfig(String stcids) {
		ShippingTemplateConfig stc = null;
		int deleteCount = 0;
		if (stcids.indexOf(",") != -1) {
			String[] ids = stcids.split(",");
			for (int i = 0; i < ids.length; i++) {
				stc = ShippingTemplateConfig.db().find(ShippingTemplateConfig.class, Long.parseLong(ids[i]));
				try {
					stc.setIs_deleted(1);
					ShippingTemplateConfig.db().update(stc);
					deleteCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			stc = ShippingTemplateConfig.db().find(ShippingTemplateConfig.class, Long.parseLong(stcids));
			try {
				stc.setIs_deleted(1);
				ShippingTemplateConfig.db().update(stc);
				deleteCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCount;
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
	public boolean STCUniqueValidate(ShippingTemplateConfig stc) {
		int findCount = 0;
		if(null == stc.getId()){
			findCount = ShippingTemplateConfig.db().find(ShippingTemplateConfig.class).where()
					.eq("shipping_template_id", stc.getShipping_template_id())
					.eq("shipping_type_id", stc.getShipping_type_id()).eq("is_deleted", 0).findRowCount();
			return findCount > 0;
		}else{
			ShippingTemplateConfig sc = ShippingTemplateConfig.db().find(ShippingTemplateConfig.class, stc.getId());
			if(stc.getShipping_template_id() != sc.getShipping_template_id() 
					|| stc.getShipping_type_id() != sc.getShipping_type_id()){
				findCount = ShippingTemplateConfig.db().find(ShippingTemplateConfig.class).where()
						.eq("shipping_template_id", stc.getShipping_template_id())
						.eq("shipping_type_id", stc.getShipping_type_id()).eq("is_deleted", 0).findRowCount();
				return findCount > 0;
			}else{
				return false;
			}
		}
	}

	/**
	 * 
	 * @Title: validateCountryAndShippingCode
	 * @Description: TODO(判断国家和发货代码是否无效)
	 * @param @param stc
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月12日
	 */
	private ValidCheckInfo validateCountryAndShippingCode(ShippingTemplateConfig stc) {
		String country = stc.getCountry();
		String shippingCode = stc.getPriority_shipping_code();
		int count = 0;
		ValidCheckInfo vci = new ValidCheckInfo();
		if (StringUtil.isNotEmpty(country)) {
			if (country.indexOf(",") != -1) {
				String[] courtrys = country.split(",");
				for (String c : courtrys) {
					count = Country.db().find(Country.class).where().eq("iso_code_two", c.toUpperCase()).eq("is_deleted", 0).findRowCount();
					if (count == 0) {
						vci.setStatus(1);
						vci.setType("country");
						vci.setDesc(c);
						return vci;
					}
				}
			} else {
				count = Country.db().find(Country.class).where().eq("iso_code_two", country.toUpperCase()).eq("is_deleted", 0)
						.findRowCount();
				if (count == 0) {
					vci.setStatus(1);
					vci.setType("country");
					vci.setDesc(country);
					return vci;
				}
			}
		}
		if (StringUtil.isNotEmpty(shippingCode)) {
			if (shippingCode.indexOf(",") != -1) {
				String[] shippingCodes = shippingCode.split(",");
				for (String sc : shippingCodes) {
					ShippingType st = ShippingType.db().find(ShippingType.class, stc.getShipping_type_id());
					if (null != st) {
						if (null != st.getShipping_code()) {
							if (!stringParseToList(st.getShipping_code()).contains(sc.toUpperCase())) {
								vci.setStatus(1);
								vci.setType("shippingCode");
								vci.setDesc(sc);
								return vci;
							}
						} else {
							vci.setStatus(1);
							vci.setType("shippingCode");
							vci.setDesc(sc);
							return vci;
						}
					} else {
						vci.setStatus(1);
						vci.setType("shippingCode");
						vci.setDesc(sc);
						return vci;
					}
				}
			} else {
				ShippingType st = ShippingType.db().find(ShippingType.class, stc.getShipping_type_id());
				if (null != st) {
					if (null != st.getShipping_code()) {
						if (!stringParseToList(st.getShipping_code()).contains(shippingCode.toUpperCase())) {
							vci.setStatus(1);
							vci.setType("shippingCode");
							vci.setDesc(shippingCode);
							return vci;
						}
					} else {
						vci.setStatus(1);
						vci.setType("shippingCode");
						vci.setDesc(shippingCode);
						return vci;
					}
				} else {
					vci.setStatus(1);
					vci.setType("shippingCode");
					vci.setDesc(shippingCode);
					return vci;
				}
			}
		}
		vci.setStatus(0);
		return vci;
	}

	/**
	 * 
	 * @Title: stringParseToList
	 * @Description: TODO(将字符串转换为字符串集合)
	 * @param @param s
	 * @param @return    
	 * @return List<String>    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月12日
	 */
	public List<String> stringParseToList(String s) {
		List<String> stringList = new ArrayList<String>();
		if (StringUtil.isNotEmpty(s)) {
			if (s.indexOf(",") != -1) {
				String[] strings = s.split(",");
				for (String str : strings) {
					stringList.add(str);
				}
			} else {
				stringList.add(s);
			}
		}
		return stringList;
	}
}

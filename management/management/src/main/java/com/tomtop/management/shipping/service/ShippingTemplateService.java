package com.tomtop.management.shipping.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.manage.model.BannerContent;
import com.tomtop.management.ebean.product.model.ProductStorageMap;
import com.tomtop.management.ebean.shipping.model.ShippingTemplate;
import com.tomtop.management.ebean.shipping.model.ShippingTemplateConfig;
import com.tomtop.management.shipping.values.ShippingTemplateValue;
import com.tomtop.management.shipping.values.ValidCheckInfo;

/**
 * 
 * @ClassName: ShippingTemplateService
 * @Description: TODO(物流模块定义服务类)
 * @author Guozy
 * @date 2016年2月19日
 *
 */
@Service
public class ShippingTemplateService {

	/**
	 * 
	 * @Title: getshippingPage
	 * @Description: TODO(分页查询广告内容)
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            bc
	 * @param @return
	 * @return Page<BannerContentObject>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月21日
	 */
	public Page<ShippingTemplateValue> getShippingTemplatePage(int pageNo, int pageLimit, ShippingTemplate template) {
		Page<ShippingTemplateValue> shippingPage = new Page<ShippingTemplateValue>();
		String query = "find ShippingTemplate where is_deleted=0 ";
		if (StringUtil.isNotEmpty(template.getTemplate_name())) {
			query += " and template_name like '%" + template.getTemplate_name() + "%'";
		}

		PagedList<ShippingTemplate> shippingPageList = ShippingTemplate.db().createQuery(ShippingTemplate.class, query)
				.orderBy("id desc").findPagedList(pageNo - 1, pageLimit);
		shippingPageList.loadRowCount();
		shippingPage.setCount(shippingPageList.getTotalRowCount());
		shippingPage.setLimit(pageLimit);
		shippingPage.setPageNo(pageNo);
		List<ShippingTemplateValue> shippingTemplateObjects = new ArrayList<ShippingTemplateValue>();
		for (ShippingTemplate obj : shippingPageList.getList()) {
			ShippingTemplateValue obext = new ShippingTemplateValue();
			try {
				BeanUtils.copyProperties(obext, obj);
				shippingTemplateObjects.add(obext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (ShippingTemplateValue templateObject : shippingTemplateObjects) {
			templateObject.setCrateTime(DateUtil.dateFormat(templateObject.getWhenCreated()));
			templateObject.setUpdateTime(DateUtil.dateFormat(templateObject.getWhenModified()));
			if (templateObject.getIs_enabled() != null) {
				if (templateObject.getIs_enabled() == 0) {
					templateObject.setEnabledStatus("Disabled");
				} else {
					templateObject.setEnabledStatus("Enabled");
				}
			}
		}

		shippingPage.setList(shippingTemplateObjects);
		return shippingPage;
	}

	/**
	 * 
	 * @Title: addShippingTemplate
	 * @Description: TODO(新增物流模块定义信息)
	 * @param @param
	 *            template
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	public int addShippingTemplate(ShippingTemplate template) {
		int addCount = 0;
		template.setIs_deleted(0);
		try {
			ShippingTemplate.db().save(template);
			addCount = 1;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: getShippingTemplateById
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
	public ShippingTemplate getShippingTemplateById(Integer id) {
		if (null != id) {
			return ShippingTemplate.db().find(ShippingTemplate.class, id);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: updateShippingTemplate
	 * @Description: TODO(通过物流模板定义编号，修改物流模块定义信息)
	 * @param @param
	 *            template
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	public int updateShippingTemplate(ShippingTemplate template) {
		int count = 0;
		try {
			ShippingTemplate.db().update(template);
			count = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 
	 * @Title: deleteShippingTemplate
	 * @Description: TODO(根据物流模块定义编号，删除相应的数据信息)
	 * @param @param
	 *            templateIds
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	public ValidCheckInfo deleteShippingTemplate(String templateIds) {
		ShippingTemplate template = null;
		ValidCheckInfo vci = new ValidCheckInfo();
		if (templateIds.indexOf(",") != -1) {
			String[] ids = templateIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				template = ShippingTemplate.db().find(ShippingTemplate.class, Integer.parseInt(ids[i]));
				vci = isCanDelete(Integer.parseInt(ids[i]));
				if(vci.getStatus() == 1){
					return vci;
				}
				try {
					template.setIs_deleted(1);
					BannerContent.db().save(template);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			template = ShippingTemplate.db().find(ShippingTemplate.class, Integer.parseInt(templateIds));
			vci = isCanDelete(Integer.parseInt(templateIds));
			if(vci.getStatus() == 1){
				return vci;
			}
			try {
				template.setIs_deleted(1);
				BannerContent.db().save(template);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		vci.setStatus(0);
		return vci;
	}

	/**
	 * 
	 * @Title: validateName
	 * @Description: TODO(根据模块名称，查询模块名称不能重复)
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return List<ShippingTemplate> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月19日
	 */
	public List<ShippingTemplate> validateName(String name) {
		return ShippingTemplate.db().find(ShippingTemplate.class).where().eq("template_name", name).eq("is_deleted", 0)
				.findList();
	}

	private ValidCheckInfo isCanDelete(Integer tid){
		ValidCheckInfo vci = new ValidCheckInfo();
		List<ShippingTemplateConfig> stcList = ShippingTemplateConfig.db().find(ShippingTemplateConfig.class).where().eq("shipping_template_id", tid).eq("is_deleted", 0).findList();
		if(stcList.size() > 0){
			vci.setStatus(1);
			vci.setType("stc");
			vci.setDesc("包含已经被配置的模版，不可删除");
			return vci;
		}else{
			List<ProductStorageMap> psmList = ProductStorageMap.db().find(ProductStorageMap.class).where().eq("ilogisticstemplateid", tid).findList();
			if(psmList.size() > 0){
				vci.setStatus(1);
				vci.setType("psm");
				vci.setDesc("包含已经与SKU关联的模版，不可删除");
				return vci;
			}
		}
		vci.setStatus(0);
		return vci;
	}
}
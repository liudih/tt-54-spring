package com.tomtop.management.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.manage.model.LoyoutModul;
import com.tomtop.management.ebean.manage.model.ModulContent;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.service.model.ModulContentObject;

@Service
public class ModulContentService {
	@Autowired
	ProductService productService;
	
	/**
	 * 
	 * @Title: getModulContentPage
	 * @Description: TODO(分页查询模块内容)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param mc
	 * @param @return    
	 * @return Page<ModulContentServiceModel>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	public Page<ModulContentObject> getModulContentPage(int pageNo, int pageLimit, ModulContent mc) {
		Page<ModulContentObject> mcPage = new Page<ModulContentObject>();
		String q = "find ModulContent where is_deleted = 0";
		if (null != mc.getClient_id()) {
			q += " and client_id = " + mc.getClient_id();
		}
		if (null != mc.getLanguage_id()) {
			q += " and language_id = " + mc.getLanguage_id();
		}
		if (null != mc.getLayout_id()) {
			q += " and layout_id = " + mc.getLayout_id();
		}
		if (null != mc.getCategory_id()) {
			q += " and category_id = " + mc.getCategory_id();
		}
		if (null != mc.getLayout_module_id()) {
			q += " and layout_module_id = " + mc.getLayout_module_id();
		}
		if (null != mc.getSku() && !mc.getSku().equals("")) {
			q += " and sku like '%" + mc.getSku() + "%'";
		}
		PagedList<ModulContent> mcPageList = ModulContent.db().createQuery(ModulContent.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		mcPageList.loadRowCount();
		mcPage.setCount(mcPageList.getTotalRowCount());
		mcPage.setLimit(pageLimit);
		mcPage.setPageNo(pageNo);
		List<ModulContentObject> mcsmList = new ArrayList<ModulContentObject>();
		for (ModulContent modulContent : mcPageList.getList()) {
			ModulContentObject mcsm = new ModulContentObject();
			try {
				BeanUtils.copyProperties(mcsm, modulContent);
				mcsmList.add(mcsm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (ModulContentObject mcsm : mcsmList) {
			if (null != mcsm.getCategory_id()) {
				mcsm.setCategory(Category.db().find(Category.class, mcsm.getCategory_id()));
			}
			if (null != mcsm.getClient_id()) {
				mcsm.setClient(Client.db().find(Client.class, mcsm.getClient_id()));
			}
			if (null != mcsm.getLanguage_id()) {
				mcsm.setLanguage(Language.db().find(Language.class, mcsm.getLanguage_id()));
			}
			mcsm.setCrateTime(DateUtil.dateFormat(mcsm.getWhenCreated()));
			mcsm.setUpdateTime(DateUtil.dateFormat(mcsm.getWhenModified()));
		}
		mcPage.setList(mcsmList);
		return mcPage;
	}

	/**
	 * 
	 * @Title: addModulContent
	 * @Description: TODO(新增模块内容)
	 * @param @param mc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	public int addModulContent(ModulContent mc) {
		int addCount = 0;
		try {
			mc.setLayout_code(Layout.db().find(Layout.class, mc.getLayout_id()).getCode());
			mc.setLayout_module_code(LoyoutModul.db().find(LoyoutModul.class, mc.getLayout_module_id()).getCode());
			mc.setListing_id(productService.getListingIdBySku(mc.getSku()));
			mc.setIs_deleted(0);
			ModulContent.db().save(mc);
			addCount = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: getMCById
	 * @Description: TODO(通过id获取模块内容)
	 * @param @param id
	 * @param @return    
	 * @return ModulContent    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	public ModulContent getMCById(Integer id) {
		return ModulContent.db().find(ModulContent.class, id);
	}

	/**
	 * 
	 * @Title: updateModulContent
	 * @Description: TODO(修改模块内容)
	 * @param @param mc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	public int updateModulContent(ModulContent mc) {
		ModulContent modulContent = ModulContent.db().find(ModulContent.class, mc.getId());
		modulContent.setClient_id(mc.getClient_id());
		modulContent.setLanguage_id(mc.getLanguage_id());
		modulContent.setLayout_id(mc.getLayout_id());
		modulContent.setLayout_module_id(mc.getLayout_module_id());
		modulContent.setIs_show(mc.getIs_show());
		modulContent.setCategory_id(mc.getCategory_id());
		modulContent.setSku(mc.getSku());
		modulContent.setSort(mc.getSort());
		modulContent.setIs_enabled(mc.getIs_enabled());
		int updateCount = 0;
		try {
			ModulContent.db().save(modulContent);
			updateCount = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	/**
	 * 
	 * @Title: deleteModulContent
	 * @Description: TODO(删除模块内容)
	 * @param @param mcIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	public int deleteModulContent(String mcIds) {
		ModulContent mc = null;
		int deleteCount = 0;
		if (mcIds.indexOf(",") != -1) {
			String[] ids = mcIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				mc = ModulContent.db().find(ModulContent.class, Integer.parseInt(ids[i]));
				try {
					mc.setIs_deleted(1);
					ModulContent.db().save(mc);
					deleteCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			mc = ModulContent.db().find(ModulContent.class, Integer.parseInt(mcIds));
			try {
				mc.setIs_deleted(1);
				ModulContent.db().save(mc);
				deleteCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCount;
	}
}

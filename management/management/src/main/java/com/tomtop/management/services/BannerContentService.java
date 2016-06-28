package com.tomtop.management.services;

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
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.service.model.BannerContentObject;

@Service
public class BannerContentService {

	/**
	 * 
	 * @Title: getBannerPage
	 * @Description: TODO(分页查询广告内容)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param bc
	 * @param @return    
	 * @return Page<BannerContentObject>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月21日
	 */
	public Page<BannerContentObject> getBannerPage(int pageNo, int pageLimit, BannerContent bc) {
		Page<BannerContentObject> bannerContentPage = new Page<BannerContentObject>();
		String q = "find Banner where is_deleted = 0";
		if (null != bc.getClient_id()) {
			q += " and client_id = " + bc.getClient_id();
		}
		if (null != bc.getLanguage_id()) {
			q += " and language_id = " + bc.getLanguage_id();
		}
		if (StringUtil.isNotEmpty(bc.getLayout_code())) {
			q += " and layout_code = '" + bc.getLayout_code()+"'";
		}
		if (StringUtil.isNotEmpty(bc.getBanner_code())) {
			q += " and banner_code = '" + bc.getBanner_code()+"'";
		}
		if (null != bc.getCategory_id()) {
			q += " and category_id = " + bc.getCategory_id();
		}
		PagedList<BannerContent> bcPageList = BannerContent.db().createQuery(BannerContent.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		bcPageList.loadRowCount();
		bannerContentPage.setCount(bcPageList.getTotalRowCount());
		bannerContentPage.setLimit(pageLimit);
		bannerContentPage.setPageNo(pageNo);
		List<BannerContentObject> bcoList = new ArrayList<BannerContentObject>();
		for (BannerContent bannerContent : bcPageList.getList()) {
			BannerContentObject bco = new BannerContentObject();
			try {
				BeanUtils.copyProperties(bco, bannerContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bcoList.add(bco);
		}
		for (BannerContentObject bco : bcoList) {
			if (null != bco.getClient_id()) {
				bco.setClient(Client.db().find(Client.class, bco.getClient_id()));
			}
			if (null != bco.getLanguage_id()) {
				bco.setLanguage(Language.db().find(Language.class, bco.getLanguage_id()));
			}
			if (null != bco.getCategory_id()) {
				bco.setCategory(Category.db().find(Category.class, bco.getCategory_id()));
			}
			bco.setCrateTime(DateUtil.dateFormat(bco.getWhenCreated()));
			bco.setUpdateTime(DateUtil.dateFormat(bco.getWhenModified()));
		}
		bannerContentPage.setList(bcoList);
		return bannerContentPage;
	}

	/**
	 * 
	 * @Title: addBannerContent
	 * @Description: TODO(新增广告内容)
	 * @param @param bc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月21日
	 */
	public int addBannerContent(BannerContent bc) {
		int addCount = 0;
		bc.setIs_deleted(0);
		try {
			BannerContent.db().save(bc);
			addCount = 1;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: getBannerContentById
	 * @Description: TODO(通过Id查询广告内容)
	 * @param @param id
	 * @param @return    
	 * @return BannerContent    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月21日
	 */
	public BannerContent getBannerContentById(Integer id) {
		if (null != id) {
			return BannerContent.db().find(BannerContent.class, id);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: updateBannerContent
	 * @Description: TODO(修改广告内容)
	 * @param @param bc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月21日
	 */
	public int updateBannerContent(BannerContent bc) {
		BannerContent bannerContent = BannerContent.db().find(BannerContent.class, bc.getId());
		bannerContent.setBanner_code(bc.getBanner_code());
		bannerContent.setClient_id(bc.getClient_id());
		if(StringUtil.isNotEmpty(bc.getImg_url())){
			bannerContent.setImg_url(bc.getImg_url());
		}
		bannerContent.setIs_enabled(bc.getIs_enabled());
		bannerContent.setLanguage_id(bc.getLanguage_id());
		bannerContent.setLayout_code(bc.getLayout_code());
		bannerContent.setName(bc.getName());
		bannerContent.setSort(bc.getSort());
		bannerContent.setTitle(bc.getTitle());
		bannerContent.setUrl(bc.getUrl());
		int updateCount = 0;
		try {
			BannerContent.db().update(bannerContent);
			updateCount = 1;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO(删除广告内容)
	 * @param @param bcIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月21日
	 */
	public int delete(String bcIds) {
		BannerContent bc = null;
		int deleteCount = 0;
		if (bcIds.indexOf(",") != -1) {
			String[] ids = bcIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				bc = BannerContent.db().find(BannerContent.class, Integer.parseInt(ids[i]));
				try {
					bc.setIs_deleted(1);
					BannerContent.db().save(bc);
					deleteCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			bc = BannerContent.db().find(BannerContent.class, Integer.parseInt(bcIds));
			try {
				bc.setIs_deleted(1);
				BannerContent.db().save(bc);
				deleteCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCount;
	}
	
	

}

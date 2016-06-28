package com.tomtop.management.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Site;
import com.tomtop.management.service.model.SiteObject;

@Service
public class SiteService {

	/**
	 * 
	 * @Title: getSitePage
	 * @Description: 查询
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param site
	 * @param @return 参数
	 * @return Page<SiteObject> 返回类型
	 * @throws
	 * @author liuxin
	 * @date 2015年12月18日
	 */
	public Page<SiteObject> getSitePage(int pageNo, int pageLimit, Site site) {
		String q = " find Site where is_deleted = 0 ";
		if (null != site.getName() && !site.getName().equals("")) {
			q += "and name like '%" + site.getName() + "%'";
		}
		Page<SiteObject> sitePage = new Page<SiteObject>();
		PagedList<Site> siteList = Site.db().createQuery(Site.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		siteList.loadRowCount();
		sitePage.setCount(siteList.getTotalRowCount());
		sitePage.setPageNo(pageNo);
		sitePage.setLimit(pageLimit);
		List<SiteObject> siList = new ArrayList<SiteObject>();
		for (Site s : siteList.getList()) {
			SiteObject siteObject = new SiteObject();
			try {
				BeanUtils.copyProperties(siteObject, s);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			siList.add(siteObject);
		}
		for (SiteObject sObject : siList) {
			sObject.setCreateTime(DateUtil.dateFormat(sObject.getWhenCreated()));
			sObject.setUpdateTime(DateUtil.dateFormat(sObject.getWhenModified()));
		}
		sitePage.setList(siList);
		return sitePage;
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加
	 * @param @param site
	 * @param @return 参数
	 * @return int 返回类型
	 * @throws
	 * @author liuxin
	 * @date 2015年12月18日
	 */
	public int addSite(Site site) {
		int count = 0;
		site.setIs_deleted(0);
		Site.db().save(site);
		count = 1;
		return count;
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改信息
	 * @param @param site
	 * @param @return 参数
	 * @return int 返回类型
	 * @throws
	 * @author liuxin
	 * @date 2015年12月18日
	 */
	public int updateSite(Site site) {
		int count = 0;
		Site s = Site.db().find(Site.class, site.getId());
		s.setName(site.getName());
		s.setDescription(site.getDescription());
		s.setIs_enabled(site.getIs_enabled());
		Site.db().update(s);
		count = 1;
		return count;
	}

	/**
	 * 
	 * @Title: checkSiteName
	 * @Description: 验证站点唯一性
	 * @param @param name
	 * @param @return 参数
	 * @return int 返回类型
	 * @throws
	 * @author liuxin
	 * @date 2015年12月18日
	 */
	public int checkName(Integer id,String name) {
		int findCount = 0 ;
		if(id==0){
			findCount = Site.db().find(Site.class).where().eq("name", name)
					.findRowCount();
		}else{
			Site site = Site.db().find(Site.class,id);
			if(!site.getName().equals(name)){
				findCount = Site.db().find(Site.class).where().eq("name", name)
						.findRowCount();
			}
		}
		return findCount;
	}

}

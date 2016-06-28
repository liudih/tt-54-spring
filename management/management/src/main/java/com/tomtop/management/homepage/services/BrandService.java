package com.tomtop.management.homepage.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.homepage.model.Brand;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Resource;
import com.tomtop.management.service.model.BrandObject;
import com.tomtop.management.services.CommonService;

@Service
public class BrandService {

	@Autowired
	CommonService commonService;
	/**
	 * 
	 * @param languages 
	 * @param clients 
	 * @Title: getBrandPage
	 * @Description: TODO(分页查询品牌信息)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param b
	 * @param @return    
	 * @return Page<BrandObject>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	public Page<BrandObject> getBrandPage(int pageNo, int pageLimit, Brand b, String clients, String languages) {
		Page<BrandObject> brandPage = new Page<BrandObject>();
		String q = "find Brand where is_deleted = 0";
		if (StringUtil.isNotEmpty(clients)) {
			q += " and client_id in (" + clients + ")";
		}else{
			List<Client> clientList = commonService.getAllClient();
			String client = null;
			if(clientList.size() > 0){
				client = "";
				for(int i=0;i<clientList.size();i++){
					if(i == 0){
						client += clientList.get(i).getId();
					}else{
						client += ","+clientList.get(i).getId();
					}
				}
			}
			q += " and client_id in ("+client+") ";
		}
		if (StringUtil.isNotEmpty(languages)) {
			q += " and language_id in (" + languages + ")";
		}
		if (StringUtil.isNotEmpty(b.getName())) {
			q += " and name like '%" + b.getName() + "%'";
		}
		if (StringUtil.isNotEmpty(b.getCode())) {
			q += " and code like '%" + b.getCode() + "%'";
		}
		PagedList<Brand> bPageList = Brand.db().createQuery(Brand.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		bPageList.loadRowCount();
		brandPage.setCount(bPageList.getTotalRowCount());
		brandPage.setLimit(pageLimit);
		brandPage.setPageNo(pageNo);
		List<BrandObject> boList = new ArrayList<BrandObject>();
		for (Brand brand : bPageList.getList()) {
			BrandObject bo = new BrandObject();
			try {
				BeanUtils.copyProperties(bo, brand);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boList.add(bo);
		}
		for (BrandObject bo : boList) {
			if (null != bo.getClient_id()) {
				bo.setClient(Client.db().find(Client.class, bo.getClient_id()));
			}
			if (null != bo.getLanguage_id()) {
				bo.setLanguage(Language.db().find(Language.class, bo.getLanguage_id()));
			}
			bo.setCrateTime(DateUtil.dateFormat(bo.getWhenCreated()));
			bo.setUpdateTime(DateUtil.dateFormat(bo.getWhenModified()));
		}
		brandPage.setList(boList);
		return brandPage;
	}

	/**
	 * 
	 * @param languages 
	 * @param clients 
	 * @Title: addBrand
	 * @Description: TODO(新增品牌)
	 * @param @param b
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	public int addBrand(Brand b, String clients, String languages) {
		List<Integer> clientList = parseIntegerList(clients);
		List<Integer> languageList = parseIntegerList(languages);
		int addCount = 0;
		List<Brand> bList = new ArrayList<Brand>();
		try {
			for(Integer clientId : clientList){
				for(Integer languageid : languageList){
					Brand brand = new Brand();
					brand.setClient_id(clientId);
					brand.setCode(b.getCode());
					brand.setDescription(b.getDescription());
					brand.setIs_deleted(0);
					brand.setIs_enabled(b.getIs_enabled());
					brand.setLanguage_id(languageid);
					brand.setLogo_url(b.getLogo_url());
					brand.setName(b.getName());
					brand.setUrl(b.getUrl());
					bList.add(brand);
				}
			}
			Brand.db().insertAll(bList);
			addCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: updateBrand
	 * @Description: TODO(修改品牌)
	 * @param @param b
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	public int updateBrand(Brand b) {
		int updateCount = 0;
		Brand brand = Brand.db().find(Brand.class, b.getId());
		brand.setCode(b.getCode());
		brand.setDescription(b.getDescription());
		brand.setIs_enabled(b.getIs_enabled());
		if(StringUtil.isNotEmpty(b.getLogo_url())){
			brand.setLogo_url(b.getLogo_url());
		}
		brand.setName(b.getName());
		brand.setUrl(b.getUrl());
		try {
			Brand.db().save(brand);
			updateCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	/**
	 * 
	 * @Title: getBrandById
	 * @Description: TODO(通过id查询品牌)
	 * @param @param id
	 * @param @return    
	 * @return Brand    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	public Brand getBrandById(Integer id) {
		return Brand.db().find(Brand.class, id);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO(删除品牌)
	 * @param @param bIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	public int delete(String bIds) {
		Brand b = null;
		int deleteCount = 0;
		if (bIds.indexOf(",") != -1) {
			String[] ids = bIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				b = Brand.db().find(Brand.class, Integer.parseInt(ids[i]));
				try {
					b.setIs_deleted(1);
					Brand.db().save(b);
					deleteCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			b = Brand.db().find(Brand.class, Integer.parseInt(bIds));
			try {
				b.setIs_deleted(1);
				Brand.db().save(b);
				deleteCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCount;
	}

	/**
	 * 
	 * @Title: brandNameUniqueValidate
	 * @Description: TODO(验证品牌名称是否重复)
	 * @param @param id
	 * @param @param brandName
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	public int brandNameUniqueValidate(Integer id, String brandName) {
		int findCount = 0;
		if (id == 0) {
			findCount = Brand.db().find(Brand.class).where().eq("name", brandName).findRowCount();
		} else {
			Brand b = Brand.db().find(Brand.class, id);
			if (!b.getName().equals(brandName)) {
				findCount = Brand.db().find(Brand.class).where().eq("name", brandName).findRowCount();
			}
		}
		return findCount;
	}
	
	/**
	 * 
	 * @Title: parseIntegerList
	 * @Description: TODO(将字符串转化为整型集合)
	 * @param @param s
	 * @param @return    
	 * @return List<Integer>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月21日
	 */
	private List<Integer> parseIntegerList(String s) {
		List<Integer> list = new ArrayList<Integer>();
		if (s.indexOf(",") != -1) {
			String[] arr = s.split(",");
			for (int i = 0; i < arr.length; i++) {
				list.add(Integer.parseInt(arr[i]));
			}
		} else {
			list.add(Integer.parseInt(s));
		}
		return list;
	}

	/**
	 * 
	 * @Title: brandUniqueValidate
	 * @Description: TODO(品牌重复校验)
	 * @param @param b
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月22日
	 */
	public boolean brandUniqueValidate(Brand b, String clients, String languages) {
		int findCount = Brand.db().find(Brand.class).where().eq("code", b.getCode()).in("client_id", clients)
				.in("language_id", languages).eq("is_deleted", 0).findRowCount();
		return findCount > 0;
	}

}

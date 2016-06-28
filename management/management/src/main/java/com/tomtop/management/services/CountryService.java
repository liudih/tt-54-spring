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
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.service.model.CountryObject;

@Service
public class CountryService {

	/**
	 * 
	 * @Title: getCountryPage
	 * @Description: TODO(分页查询国家)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param c
	 * @param @return    
	 * @return Page<CountryObject>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	public Page<CountryObject> getCountryPage(int pageNo, int pageLimit, Country c) {
		Page<CountryObject> countryPage = new Page<CountryObject>();
		String q = "find Country where is_deleted = 0";
		if (StringUtil.isNotEmpty(c.getName())) {
			q += " and name like '%" + c.getName() + "%'";
		}
		PagedList<Country> cPageList = Country.db().createQuery(Country.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		cPageList.loadRowCount();
		countryPage.setCount(cPageList.getTotalRowCount());
		countryPage.setLimit(pageLimit);
		countryPage.setPageNo(pageNo);
		List<CountryObject> coList = new ArrayList<CountryObject>();
		for (Country country : cPageList.getList()) {
			CountryObject co = new CountryObject();
			try {
				BeanUtils.copyProperties(co, country);
			} catch (Exception e) {
				e.printStackTrace();
			}
			coList.add(co);
		}
		for (CountryObject co : coList) {
			if (null != co.getOfficial_language_id()) {
				co.setOfficialLanguage(Language.db().find(Language.class, co.getOfficial_language_id()));
			}
			if (null != co.getLanguage_id()) {
				co.setLanguage(Language.db().find(Language.class, co.getLanguage_id()));
			}
			co.setCrateTime(DateUtil.dateFormat(co.getWhenCreated()));
			co.setUpdateTime(DateUtil.dateFormat(co.getWhenModified()));
		}
		countryPage.setList(coList);
		return countryPage;
	}

	/**
	 * 
	 * @Title: addCountry
	 * @Description: TODO(新增国家)
	 * @param @param c
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	public int addCountry(Country c) {
		int addCount = 0;
		c.setIs_deleted(0);
		try {
			Country.db().save(c);
			addCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: getCountryById
	 * @Description: TODO(通过id查询国家)
	 * @param @param id
	 * @param @return    
	 * @return Country    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	public Country getCountryById(Integer id) {
		return Country.db().find(Country.class, id);
	}

	/**
	 * 
	 * @Title: updateCountry
	 * @Description: TODO(修改国家)
	 * @param @param c
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	public int updateCountry(Country c) {
		int updateCount = 0;
		Country country = Country.db().find(Country.class, c.getId());
		country.setAddress_format(c.getAddress_format());
		country.setCurrency(c.getCurrency());
		country.setIs_enabled(c.getIs_enabled());
		country.setIs_required_postcode(c.getIs_required_postcode());
		country.setIso_code_three(c.getIso_code_three());
		country.setLanguage_id(c.getLanguage_id());
		country.setLength_unit(c.getLength_unit());
		country.setName(c.getName());
		if(StringUtil.isNotEmpty(c.getNational_flag_img_url())){
			country.setNational_flag_img_url(c.getNational_flag_img_url());
		}
		country.setOfficial_language_id(c.getOfficial_language_id());
		country.setSort(c.getSort());
		country.setWeigth_unit(c.getWeigth_unit());
		try {
			Country.db().save(country);
			updateCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	/**
	 * 
	 * @Title: deleteCountry
	 * @Description: TODO(删除国家)
	 * @param @param cIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	public int deleteCountry(String cIds) {
		Country c = null;
		int deleteCount = 0;
		if (cIds.indexOf(",") != -1) {
			String[] ids = cIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				c = Country.db().find(Country.class, Integer.parseInt(ids[i]));
				try {
					c.setIs_deleted(1);
					Country.db().save(c);
					deleteCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			c = Country.db().find(Country.class, Integer.parseInt(cIds));
			try {
				c.setIs_deleted(1);
				Country.db().save(c);
				deleteCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCount;
	}

	/**
	 * 
	 * @Title: countryNameUniqueValidate
	 * @Description: TODO(国家名称唯一校验)
	 * @param @param id
	 * @param @param countryName
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	public int countryNameUniqueValidate(Integer id, String countryName) {
		int findCount = 0;
		if (id == 0) {
			findCount = Country.db().find(Country.class).where().eq("name", countryName).findRowCount();
		} else {
			Country c = Country.db().find(Country.class, id);
			if (!c.getName().equals(countryName)) {
				findCount = Country.db().find(Country.class).where().eq("name", countryName).findRowCount();
			}
		}
		return findCount;
	}

}

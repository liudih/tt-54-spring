package com.tomtop.management.homepage.services;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.PersistBatch;
import com.google.common.collect.Lists;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.homepage.model.DailyDeal;
import com.tomtop.management.ebean.homepage.model.FeaturedCategory;

import com.tomtop.management.ebean.homepage.model.FeaturedCategoryBanner;
import com.tomtop.management.ebean.homepage.model.FeaturedCategoryKey;
import com.tomtop.management.ebean.homepage.model.FeaturedCategorySku;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.homepage.service.values.FeatureCategoryBannerValue;
import com.tomtop.management.homepage.service.values.FeatureCategoryKeyValue;
import com.tomtop.management.homepage.service.values.FeatureCategorySkuValue;
import com.tomtop.management.homepage.service.values.FeatureCategoryValue;
import com.tomtop.management.service.model.FeaturedCategoryObject;
import com.tomtop.management.services.BaseParameterService;
import com.tomtop.management.services.CommonService;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @ClassName: FeaturedCategoryService
 * @Description: TODO(特色类目服务类)
 * @author Guozy
 * @date 2015年12月25日
 *
 */
@Service
public class FeaturedCategoryService {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	FeaturedCategorySkuService featuredCategorySkuService;

	@Autowired
	FeaturedCategoryBannerService featuredCategoryBannerService;

	@Autowired
	FeaturedCategoryKeyService featuredCategoryKeyService;

	@Autowired
	BaseParameterService baseParameterService;

	@Autowired
	CommonService commonService;

	public Page<FeaturedCategoryObject> getFeaturedCategoryPage(int pageNo, int pageLimit,
			FeaturedCategory featuredCategory, String clients, String languages, String categorys) {
		Page<FeaturedCategoryObject> featuredCategoryage = new Page<FeaturedCategoryObject>();
		String query = "find FeaturedCategory where  is_deleted=0 ";
		String clientStr = "";
		if (StringUtil.isNotEmpty(clients)) {
			query += " and client_id in (" + clients + ") ";
		} else {
			List<Client> clientss = commonService.getAllClient();
			if (clientss.size() > 0 && clientss != null) {
				for (int i = 0; i < clientss.size(); i++) {
					if (i == 0) {
						clientStr = clientss.get(i).getId() + "";
					} else {
						clientStr += "," + clientss.get(i).getId();
					}
				}
				query += " and client_id in (" + clientStr + ") ";
			}
		}
		if (StringUtil.isNotEmpty(languages)) {
			query += " and language_id in (" + languages + ") ";
		}

		if (StringUtil.isNotEmpty(categorys)) {
			query += " and category_id in (" + categorys + ") ";
		}

		PagedList<FeaturedCategory> featuredCategoryList = FeaturedCategory.db()
				.createQuery(FeaturedCategory.class, query).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		featuredCategoryList.loadRowCount();
		featuredCategoryage.setCount(featuredCategoryList.getTotalRowCount());
		featuredCategoryage.setLimit(pageLimit);
		featuredCategoryage.setPageNo(pageNo);
		List<FeaturedCategoryObject> featuredCategoryObjects = new ArrayList<FeaturedCategoryObject>();
		for (FeaturedCategory featured : featuredCategoryList.getList()) {
			FeaturedCategoryObject obj = new FeaturedCategoryObject();
			try {
				BeanUtils.copyProperties(obj, featured);
				featuredCategoryObjects.add(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (FeaturedCategoryObject obj : featuredCategoryObjects) {
			if (obj.getClient_id() != null) {
				obj.setClient(Client.db().find(Client.class, obj.getClient_id()));
			}
			if (obj.getLanguage_id() != null) {
				obj.setLanguage(Language.db().find(Language.class, obj.getLanguage_id()));
			}
			if (obj.getCategory_id() != null) {
				obj.setCategory(Category.db().find(Category.class, obj.getCategory_id()));
			}
			obj.setCreateTime(DateUtil.dateFormat(obj.getWhenCreated()));
			obj.setUpdateTime(DateUtil.dateFormat(obj.getWhenModified()));
			if (obj.getIs_enabled() != null) {
				if (obj.getIs_enabled() == 0) {
					obj.setEnabledStatus("Disabled");
				} else {
					obj.setEnabledStatus("Enabled");
				}
			}
		}

		featuredCategoryage.setList(featuredCategoryObjects);
		return featuredCategoryage;
	}

	/**
	 * 
	 * @Title: getBannerId
	 * @Description: TODO( 根据编号，获取特色类目的数据信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return FeaturedCategory 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月25日
	 */
	public FeatureCategoryValue getFeaturedCategorylId(int id) {
		FeatureCategoryValue featureCategoryValue = new FeatureCategoryValue();
		FeaturedCategory featuredCategory = FeaturedCategory.db().find(FeaturedCategory.class, id);
		if (featuredCategory != null) {
			try {
				List<Integer> clients = new ArrayList<Integer>();
				BeanUtils.copyProperties(featureCategoryValue, featuredCategory);
				clients.add(featuredCategory.getClient_id());
				featureCategoryValue.setClients(clients);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<FeaturedCategoryKey> keylist = FeaturedCategoryKey.db().find(FeaturedCategoryKey.class).where()
					.eq("featured_category_id", featuredCategory.getId()).eq("is_deleted", 0).order()
					.desc("whenModified").findList();
			List<FeaturedCategorySku> skus = FeaturedCategorySku.db().find(FeaturedCategorySku.class).where()
					.eq("featured_category_id", featuredCategory.getId()).eq("is_deleted", 0).order()
					.desc("whenModified").findList();
			List<FeaturedCategoryBanner> banners = FeaturedCategoryBanner.db().find(FeaturedCategoryBanner.class)
					.where().eq("featured_category_id", featuredCategory.getId()).eq("is_deleted", 0).order()
					.desc("whenModified").findList();
			try {
				if (keylist.size() > 0) {
					List<FeatureCategoryKeyValue> values = new ArrayList<FeatureCategoryKeyValue>();
					for (FeaturedCategoryKey featuredCategoryKey : keylist) {
						FeatureCategoryKeyValue value = new FeatureCategoryKeyValue();
						BeanUtils.copyProperties(value, featuredCategoryKey);
						value.setKey(featuredCategoryKey.getKeyword());
						values.add(value);
					}
					featureCategoryValue.setKeys(values);
				}
				if (skus.size() > 0) {
					List<FeatureCategorySkuValue> values = new ArrayList<FeatureCategorySkuValue>();
					for (FeaturedCategorySku sku : skus) {
						FeatureCategorySkuValue value = new FeatureCategorySkuValue();
						BeanUtils.copyProperties(value, sku);
						values.add(value);
					}
					featureCategoryValue.setSkus(values);
				}
				if (banners.size() > 0) {
					List<FeatureCategoryBannerValue> values = new ArrayList<FeatureCategoryBannerValue>();
					for (FeaturedCategoryBanner banner : banners) {
						FeatureCategoryBannerValue value = new FeatureCategoryBannerValue();
						BeanUtils.copyProperties(value, banner);
						BaseParameter parameter = baseParameterService.getBaseParameterByid(banner.getPosition_id());
						value.setPositionName(parameter.getName());
						values.add(value);

					}
					featureCategoryValue.setBanners(values);
					;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return featureCategoryValue;
	};

	/**
	 * 
	 * @Title: delById
	 * @Description: TODO(通过编号，删除数据信息)
	 * @param @param
	 *            param
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public int delById(String param) {
		FeaturedCategory featuredCategory = null;
		int count = 0;
		if (param.indexOf(",") != -1) {
			String[] sourceStrArray = param.split(",");
			for (int i = 0; i < sourceStrArray.length; i++) {
				featuredCategory = FeaturedCategory.db().find(FeaturedCategory.class, sourceStrArray[i]);
				deleteFeaturedCategorys(featuredCategory);
				count++;
			}
		} else {
			featuredCategory = FeaturedCategory.db().find(FeaturedCategory.class, param);
			deleteFeaturedCategorys(featuredCategory);
			count++;
		}
		return count;
	}

	/**
	 * 
	 * @Title: getFeaturedCategorys
	 * @Description: TODO(获取参数类目的信息)
	 * @param @return
	 *            参数
	 * @return List<FeaturedCategory> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月30日
	 */
	public List<FeaturedCategory> getFeaturedCategorys() {
		return FeaturedCategory.db().find(FeaturedCategory.class).where().eq("is_deleted", 0).orderBy("id desc")
				.findList();
	}

	/**
	 * @Title: insert
	 * @Description: 批量保存-开启事务
	 * @param @param
	 *            fcList
	 * @param @throws
	 *            IOException 参数
	 * @return void 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2016年1月11日
	 */
	public void insert(List<FeaturedCategory> fcsList, Transaction paramTransaction) throws IOException {
		FeaturedCategory.db().insertAll(fcsList, paramTransaction);
	}

	/**
	 * @Title: Insert
	 * @Description: 批量插入数据到数据库
	 * @param @param
	 *            fcboList 参数
	 * @return void 返回类型
	 * @throws Exception
	 * @throws @author
	 *             Administrator
	 * @date 2016年1月11日
	 */
	public void insert(FeatureCategoryValue fcbv) throws Exception {
		Transaction ts = null;
		try {
			ts = FeaturedCategory.db().beginTransaction();
			ts.setBatchSize(50);
			ts.setBatch(PersistBatch.ALL);
			ts.setBatchOnCascade(PersistBatch.INSERT);
			ts.setBatchGetGeneratedKeys(false);
			List<FeaturedCategory> list = Lists.transform(fcbv.getClients(), p -> {
				FeaturedCategory fc = new FeaturedCategory();
				fc.setCategory_id(fcbv.getCategory_id());
				fc.setClient_id(p);
				fc.setImg_url(fcbv.getImg_url());
				fc.setIs_deleted(0);
				fc.setIs_enabled(fcbv.getIs_enabled());
				fc.setLanguage_id(fcbv.getLanguage_id());
				fc.setNumber(fcbv.getNumber());
				fc.setSort(fcbv.getSort());
				return fc;
			});
			this.insert(list, ts);
			List<FeaturedCategory> dblist = FeaturedCategory.db().find(FeaturedCategory.class).where()
					.in("client_id", fcbv.getClients()).eq("language_id", fcbv.getLanguage_id())
					.eq("category_id", fcbv.getCategory_id()).findList();

			for (FeaturedCategory dbfc : dblist) {
				featuredCategorySkuService.insert(fcbv.getSkus(), dbfc.getId().intValue(), dbfc.getLanguage_id(),
						dbfc.getClient_id().intValue(), ts);
				featuredCategoryBannerService.insert(fcbv.getBanners(), dbfc.getId().intValue(), dbfc.getLanguage_id(),
						dbfc.getClient_id(), ts);
				featuredCategoryKeyService.insert(fcbv.getKeys(), dbfc.getId().intValue(), dbfc.getLanguage_id(),
						dbfc.getClient_id(), ts);
			}
			ts.commit();
		} catch (Exception ex) {
			logger.error("Feature Category insert error ->", ex);
			ts.rollback();
			throw ex;
		}
	}

	/**
	 * @Title: Insert
	 * @Description: 批量插入数据到数据库
	 * @param @param
	 *            fcboList 参数
	 * @return void 返回类型
	 * @throws Exception
	 * @throws @author
	 *             Administrator
	 * @date 2016年1月11日
	 */
	public void update(FeatureCategoryValue fcbv) throws Exception {
		Transaction ts = null;
		try {
			ts = FeaturedCategory.db().beginTransaction();
			ts.setBatchSize(50);
			ts.setBatchGetGeneratedKeys(false);
			List<FeaturedCategory> list = Lists.transform(fcbv.getClients(), p -> {
				FeaturedCategory fc = new FeaturedCategory();
				fc.setId(fcbv.getId());
				fc.setCategory_id(fcbv.getCategory_id());
				fc.setClient_id(p);
				fc.setImg_url(fcbv.getImg_url());
				fc.setIs_deleted(0);
				fc.setIs_enabled(fcbv.getIs_enabled());
				fc.setLanguage_id(fcbv.getLanguage_id());
				fc.setNumber(fcbv.getNumber());
				fc.setSort(fcbv.getSort());
				return fc;
			});
			FeaturedCategory.db().updateAll(list, ts);

			for (FeaturedCategory dbfc : list) {
				featuredCategorySkuService.update(fcbv.getSkus(), dbfc.getId().intValue(), dbfc.getLanguage_id(),
						dbfc.getClient_id().intValue(), ts);
				featuredCategoryBannerService.update(fcbv.getBanners(), dbfc.getId().intValue(), dbfc.getLanguage_id(),
						dbfc.getClient_id(), ts);
				featuredCategoryKeyService.update(fcbv.getKeys(), dbfc.getId().intValue(), dbfc.getLanguage_id(),
						dbfc.getClient_id(), ts);
			}
			ts.commit();
		} catch (Exception ex) {
			logger.error("Feature Category insert error ->", ex);
			ts.rollback();
			throw ex;
		}
	}

	/**
	 * 
	 * @Title: validateFeaturedCategory
	 * @Description: TODO(验证语言和客户端和类目)
	 * @param @param
	 *            featuredCategory
	 * @param @return
	 *            参数
	 * @return List<FeaturedCategory> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月14日
	 */
	public List<FeaturedCategory> validateFeaturedCategory(FeaturedCategory featuredCategory, String clients) {
		String query = "find FeaturedCategory where is_deleted=0 ";
		if (StringUtil.isNotEmpty(clients)) {
			query += " and client_id in (" + clients.substring(0, clients.length() - 1) + ") ";
		}
		if (null != featuredCategory.getLanguage_id()) {
			query += " and language_id = " + featuredCategory.getLanguage_id();
		}
		if (null != featuredCategory.getCategory_id()) {
			query += " and category_id=" + featuredCategory.getCategory_id();
		}
		List<FeaturedCategory> featuredCategorys = FeaturedCategory.db().createQuery(FeaturedCategory.class, query)
				.orderBy("id desc").findList();
		return featuredCategorys;
	};

	/**
	 * 
	 * @Title: deleteFeaturedCategorys
	 * @Description: TODO(删除数据信息)
	 * @param @return
	 *            参数
	 * @return FeaturedCategory 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2016年1月15日
	 */
	public void deleteFeaturedCategorys(FeaturedCategory featuredCategory) {
		List<FeaturedCategoryBanner> banners = FeaturedCategoryBanner.db().find(FeaturedCategoryBanner.class).where()
				.eq("featured_category_id", featuredCategory.getCategory_id()).findList();
		for (FeaturedCategoryBanner featuredCategoryBanner : banners) {
			featuredCategoryBanner.setIs_deleted(1);
			FeaturedCategoryBanner.db().update(featuredCategoryBanner);
		}
		List<FeaturedCategoryKey> keys = FeaturedCategoryKey.db().find(FeaturedCategoryKey.class).where()
				.eq("featured_category_id", featuredCategory.getCategory_id()).findList();
		for (FeaturedCategoryKey featuredCategoryKey : keys) {
			featuredCategoryKey.setIs_deleted(1);
		}
		List<FeaturedCategorySku> skus = FeaturedCategorySku.db().find(FeaturedCategorySku.class).where()
				.eq("featured_category_id", featuredCategory.getCategory_id()).findList();
		for (FeaturedCategorySku featuredCategorySku : skus) {
			featuredCategorySku.setIs_deleted(1);
		}
		featuredCategory.setIs_deleted(1);
		FeaturedCategory.db().save(featuredCategory);
	};

}

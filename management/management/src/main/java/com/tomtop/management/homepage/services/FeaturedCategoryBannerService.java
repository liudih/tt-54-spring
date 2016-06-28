package com.tomtop.management.homepage.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.annotation.Transactional;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.tomtop.management.ebean.homepage.model.FeaturedCategoryBanner;
import com.tomtop.management.ebean.homepage.model.FeaturedCategorySku;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.homepage.service.values.FeatureCategoryBannerValue;
import com.tomtop.management.homepage.service.values.FeatureCategorySkuValue;
import com.tomtop.management.service.model.FeaturedCategoryBannerObject;

@Service
public class FeaturedCategoryBannerService {

	/**
	 * 
	 * @Title: geFeaturedCategorySkus
	 * @Description: TODO(获取key的数据信息)
	 * @param @return 参数
	 * @return List<FeaturedCategorySku> 返回类型
	 * @throws @author Guozy
	 * @date 2015年12月30日
	 */
	public List<FeaturedCategoryBannerObject> getFeaturedCategoryBanners() {
		List<FeaturedCategoryBanner> banners = FeaturedCategoryBanner.db()
				.find(FeaturedCategoryBanner.class).where().eq("is_deleted", 0)
				.findList();
		List<FeaturedCategoryBannerObject> objects = new ArrayList<FeaturedCategoryBannerObject>();
		for (FeaturedCategoryBanner layo : banners) {
			FeaturedCategoryBannerObject lay = new FeaturedCategoryBannerObject();
			try {
				BeanUtils.copyProperties(lay, layo);
				objects.add(lay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (FeaturedCategoryBannerObject obj : objects) {
			if (null != obj.getPosition_id()) {
				BaseParameter baseParameter = new BaseParameter();
				baseParameter.setValue(obj.getPosition_id());
				List<BaseParameter> baseParameters = BaseParameter.db()
						.find(BaseParameter.class).where().eq("is_deleted", 0)
						.eq("value", baseParameter.getValue())
						.eq("type", "BANNER-POSITION").findList();
				obj.setParameter(baseParameters.get(0));
			}
		}
		return objects;
	}

	/**
	 * @Title: insert
	 * @Description: 批量插入--开启事务
	 * @param @param fcbList
	 * @param @throws IOException 参数
	 * @return void 返回类型
	 * @throws
	 * @author fcl
	 * @date 2016年1月11日
	 */
	public void insert(List<FeaturedCategoryBanner> fcbList,
			Transaction paramTransaction) throws IOException {
		FeaturedCategoryBanner.db().insertAll(fcbList, paramTransaction);
	}

	/**
	 * @Title: insert
	 * @Description: 批量插入--开启事务
	 * @param @param fckList
	 * @param @throws IOException 参数
	 * @return void 返回类型
	 * @throws
	 * @author fcl
	 * @date 2016年1月11日
	 */
	public void insert(List<FeatureCategoryBannerValue> fckList,
			Integer featureCategoryId, Integer langid, Integer clientId,
			Transaction paramTransaction) throws IOException {
		if (fckList == null || fckList.size() == 0) {
			return;
		}
		List<FeaturedCategoryBanner> list = Lists.transform(fckList, p -> {
			FeaturedCategoryBanner psku = new FeaturedCategoryBanner();
			psku.setClient_id(clientId);
			psku.setFeatured_category_id(featureCategoryId);
			psku.setImg_url(p.getImg_url());
			psku.setIs_deleted(0);
			psku.setIs_enabled(p.getIs_enabled());
			psku.setLanguage_id(langid);
			psku.setPosition_id(p.getPosition_id());
			psku.setSort(p.getSort());
			psku.setTitle(p.getTitle());
			psku.setUrl(p.getUrl());
			return psku;
		});
		FeaturedCategoryBanner.db().insertAll(list, paramTransaction);
	}

	public void update(List<FeatureCategoryBannerValue> fbannerList,
			Integer featureCategoryId, Integer langid, Integer clientId,
			Transaction paramTransaction) throws IOException {
		if (fbannerList == null || fbannerList.size() == 0) {
			return;
		}
		// ~ update
		Multimap<Long, FeatureCategoryBannerValue> maps = Multimaps.index(
				fbannerList, p -> {
					if (p.getId() == null)
						return -1l;
					return p.getId();
				});

		List<FeaturedCategoryBanner> dbbanners = FeaturedCategoryBanner.db()
				.find(FeaturedCategoryBanner.class).where()
				.eq("featured_category_id", featureCategoryId)
				.eq("is_deleted", 0).findList();
		List<FeaturedCategoryBanner> updateskus = com.google.common.collect.FluentIterable
				.from(dbbanners)
				.transform(
						p -> {
							if (maps.keySet().contains(p.getId()) == false) {
								p.setIs_deleted(1);
							} else {
								FeatureCategoryBannerValue skuval = (FeatureCategoryBannerValue) maps
										.get(p.getId()).toArray()[0];
								p.setIs_enabled(skuval.getIs_enabled());
								p.setUrl(skuval.getUrl());
								p.setSort(skuval.getSort());
								p.setImg_url(skuval.getImg_url());
								p.setTitle(skuval.getTitle());
								p.setPosition_id(skuval.getPosition_id());
								
							}
							return p;
						}).toList();
		FeaturedCategorySku.db().updateAll(updateskus, paramTransaction);
		if(maps.containsKey(-1l)){
		this.insert(Lists.newArrayList(maps.get(-1l)), featureCategoryId, langid, clientId,
				paramTransaction);
		}
	}

}

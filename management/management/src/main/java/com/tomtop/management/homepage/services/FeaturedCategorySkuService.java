package com.tomtop.management.homepage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.annotation.Transactional;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.tomtop.management.ebean.homepage.model.FeaturedCategorySku;
import com.tomtop.management.homepage.service.values.FeatureCategorySkuValue;

/**
 * 
 * @ClassName: FeaturedCategoryService
 * @Description: TODO(Sku服务类)
 * @author Guozy
 * @date 2015年12月25日
 *
 */
@Service
public class FeaturedCategorySkuService {

	/**
	 * 
	 * @Title: geFeaturedCategorySkus
	 * @Description: TODO(获取sku的数据信息)
	 * @param @return
	 *            参数
	 * @return List<FeaturedCategorySku> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月30日
	 */
	public List<FeaturedCategorySku> getFeaturedCategorySkus() {
		return FeaturedCategorySku.db().find(FeaturedCategorySku.class).where().eq("is_deleted", 0).findList();
	};

	/**
	 * @Title: insert
	 * @Description: 批量插入--开启事务
	 * @param @param
	 *            fckList
	 * @param @throws
	 *            IOException 参数
	 * @return void 返回类型
	 * @throws @author
	 *             fcl
	 * @date 2016年1月11日
	 */
	public void insert(List<FeaturedCategorySku> fckList, Transaction paramTransaction) throws IOException {
		FeaturedCategorySku.db().insertAll(fckList, paramTransaction);
	}

	/**
	 * @Title: insert
	 * @Description: 批量插入--开启事务
	 * @param @param
	 *            fckList
	 * @param @throws
	 *            IOException 参数
	 * @return void 返回类型
	 * @throws @author
	 *             fcl
	 * @date 2016年1月11日
	 */
	public void insert(List<FeatureCategorySkuValue> fckList, Integer featureCategoryId, Integer langid,
			Integer clientId, Transaction paramTransaction) throws IOException {
		if (fckList == null || fckList.size() == 0) {
			return;
		}
		List<FeaturedCategorySku> list = Lists.transform(fckList, p -> {
			FeaturedCategorySku psku = new FeaturedCategorySku();
			psku.setClient_id(clientId);
			psku.setFeatured_category_id(featureCategoryId);
			psku.setIs_deleted(0);
			psku.setIs_enabled(p.getIs_enabled());
			psku.setLanguage_id(langid);
			psku.setListing_id(p.getListing_id());
			psku.setSku(p.getSku());
			psku.setSort(p.getSort());
			return psku;
		});
		FeaturedCategorySku.db().insertAll(list, paramTransaction);
	}

	/**
	 * @Title: update
	 * @Description: 更新--比较集合的差异--开始
	 * @param @param
	 *            fckList
	 * @param @param
	 *            featureCategoryId
	 * @param @param
	 *            langid
	 * @param @param
	 *            clientId
	 * @param @param
	 *            paramTransaction
	 * @param @throws
	 *            IOException 参数
	 * @return void 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2016年1月12日
	 */
	public void update(List<FeatureCategorySkuValue> fckList, Integer featureCategoryId, Integer langid,
			Integer clientId, Transaction paramTransaction) throws IOException {
		if (fckList == null || fckList.size() == 0) {
			return;
		}
		// ~ update
		Multimap<String, FeatureCategorySkuValue> maps = Multimaps.index(fckList, p -> p.getSku());
		List<FeaturedCategorySku> dbskus = FeaturedCategorySku.db().find(FeaturedCategorySku.class).where()
				.eq("featured_category_id", featureCategoryId).eq("is_deleted", 0).findList();
		List<FeaturedCategorySku> updateskus = com.google.common.collect.FluentIterable.from(dbskus).transform(p -> {
			if (maps.keySet().contains(p.getSku()) == false) {
				p.setIs_deleted(1);
			} else {
				FeatureCategorySkuValue skuval = (FeatureCategorySkuValue) maps.get(p.getSku()).toArray()[0];
				p.setIs_enabled(skuval.getIs_enabled());
				p.setSort(skuval.getSort());
			}
			return p;
		}).toList();
		FeaturedCategorySku.db().updateAll(updateskus, paramTransaction);
		// ~ insert
		List<String> dbsku = Lists.transform(dbskus, p -> p.getSku());
		List<FeatureCategorySkuValue> insertskus = com.google.common.collect.FluentIterable.from(fckList)
				.filter(p -> dbsku.contains(p.getSku()) == false).toList();
		this.insert(insertskus, featureCategoryId, langid, clientId, paramTransaction);
	}

	/**
	 * 
	 * @Title: validateSkuExist
	 * @Description: TODO(验证sku是否存在)
	 * @param @param
	 *            sku
	 * @param @return
	 *            参数
	 * @return List<FeaturedCategorySku> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月14日
	 */
	public List<FeaturedCategorySku> validateSkuExist(String sku) {
		List<FeaturedCategorySku> skuList = FeaturedCategorySku.db().find(FeaturedCategorySku.class).where()
				.eq("is_deleted", 0).eq("sku", sku).findList();
		return skuList;
	};

}

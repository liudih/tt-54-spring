package com.tomtop.management.homepage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.annotation.Transactional;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.tomtop.management.ebean.homepage.model.FeaturedCategoryKey;
import com.tomtop.management.ebean.homepage.model.FeaturedCategorySku;
import com.tomtop.management.homepage.service.values.FeatureCategoryKeyValue;
import com.tomtop.management.homepage.service.values.FeatureCategorySkuValue;

/**
 * 
 * @ClassName: FeaturedCategoryService
 * @Description: TODO(服务类)
 * @author Guozy
 * @date 2015年12月25日
 *
 */
@Service
public class FeaturedCategoryKeyService {

	/**
	 * 
	 * @Title: geFeaturedCategorySkus
	 * @Description: TODO(获取key的数据信息)
	 * @param @return 参数
	 * @return List<FeaturedCategorySku> 返回类型
	 * @throws @author Guozy
	 * @date 2015年12月30日
	 */
	public List<FeaturedCategoryKey> getFeaturedCategoryKeys() {
		return FeaturedCategoryKey.db().find(FeaturedCategoryKey.class).where()
				.eq("is_deleted", 0).findList();
	}

	/**
	 * @Title: insert
	 * @Description: 批量插入--开启事务
	 * @param @param fckList
	 * @param @throws IOException 参数
	 * @return void 返回类型
	 * @throws
	 * @author Administrator
	 * @date 2016年1月11日
	 */
	public void insert(List<FeaturedCategoryKey> fckList,
			Transaction paramTransaction) throws IOException {
		FeaturedCategoryKey.db().insertAll(fckList);
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
	public void insert(List<FeatureCategoryKeyValue> fckList,
			Integer featureCategoryId, Integer langid, Integer clientId,
			Transaction paramTransaction) throws IOException {
		if (fckList == null || fckList.size() == 0) {
			return;
		}
		List<FeaturedCategoryKey> list = Lists.transform(fckList, p -> {
			FeaturedCategoryKey psku = new FeaturedCategoryKey();
			psku.setClient_id(clientId);
			psku.setFeatured_category_id(featureCategoryId);
			psku.setIs_deleted(0);
			psku.setIs_enabled(p.getIs_enabled());
			psku.setLanguage_id(langid);
			psku.setKeyword(p.getKey());
			psku.setSort(p.getSort());
			return psku;
		});
		FeaturedCategoryKey.db().insertAll(list, paramTransaction);
	}

	public void update(List<FeatureCategoryKeyValue> fckList,
			Integer featureCategoryId, Integer langid, Integer clientId,
			Transaction paramTransaction) throws IOException {
		if (fckList == null || fckList.size() == 0) {
			return;
		}
		// ~ update
		Multimap<String, FeatureCategoryKeyValue> maps = Multimaps.index(
				fckList, p -> p.getKey());
		List<FeaturedCategoryKey> dbkeys = FeaturedCategoryKey.db()
				.find(FeaturedCategoryKey.class).where()
				.eq("featured_category_id", featureCategoryId)
				.eq("is_deleted", 0).findList();
		List<FeaturedCategoryKey> updatekeys = com.google.common.collect.FluentIterable
				.from(dbkeys)
				.transform(
						p -> {
							if (maps.keySet().contains(p.getKeyword()) == false) {
								p.setIs_deleted(1);
							} else {
								FeatureCategoryKeyValue skuval = (FeatureCategoryKeyValue) maps
										.get(p.getKeyword()).toArray()[0];
								p.setIs_enabled(skuval.getIs_enabled());
								p.setSort(skuval.getSort());
							}
							return p;
						}).toList();

		FeaturedCategoryKey.db().updateAll(updatekeys, paramTransaction);
		// ~ insert
		List<String> dbKeyword = Lists.transform(updatekeys,
				p -> p.getKeyword());
		List<FeatureCategoryKeyValue> insertkeys = com.google.common.collect.FluentIterable
				.from(fckList)
				.filter(p -> dbKeyword.contains(p.getKey()) == false).toList();
		this.insert(insertkeys, featureCategoryId, langid, clientId,
				paramTransaction);
	}

}

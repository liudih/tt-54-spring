package com.tomtop.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.tomtop.common.BaseServiceUtil;
import com.tomtop.common.Config;
import com.tomtop.entity.Constant;
import com.tomtop.entity.IndexEntity;
import com.tomtop.entity.Language;
import com.tomtop.entity.MutilLanguage;
import com.tomtop.entity.ProductEntity;

/**
 * 索引基础信息
 * 
 * @author ztiny
 */
public abstract class BaseIndexInfo {

	Logger logger = Logger.getLogger(BaseIndexInfo.class);
	
	// 局部更新索引时会用到
	public static final JSONObject ROUTE;
	public static final String[] INDEX_ALL;
	static {
		String routes = Config.getValue("routes");
		ROUTE = JSONObject.parseObject(routes);

		String indexAllStr = Config.getValue("product.all");
		INDEX_ALL = indexAllStr.split(",");
	}

	/**
	 * 获取索引名称
	 * 
	 * @param product
	 * @return
	 */
	public List<String> getIndexNames() {
		List<String> languages = new ArrayList<String>();
		for (String index : BaseIndexInfo.INDEX_ALL) {
			String indexName = getIndexName(index);
			languages.add(indexName);
		}
		return languages;
	}

	/**
	 * 根据语言解析出不同版本的产品对象
	 * 
	 * @param product
	 * @return Map<Stirng,IndexEntity> 域名为键，值IndexEntity实体类
	 */
	public Map<String, IndexEntity> getMutilLanguagesProduct(
			ProductEntity product) throws Exception {
		if (product == null || product.getMutil() == null|| product.getMutil().size() < 1) {
			return null;
		}
		Map<String, Language> languageMap = BaseServiceUtil.getLanguageMap();
		// 解析product已经国际化的语言
		Map<String, MutilLanguage> domainsMap = getDomains(product);
		Map<String, IndexEntity> map = new HashMap<String, IndexEntity>();
		for (int i = 0; i < INDEX_ALL.length; i++) {
			MutilLanguage mutil = null;
			IndexEntity indexModel = new IndexEntity();
			BeanUtils.copyProperties(product, indexModel, "mutil");
			// 判断产品是否已经国际化了该语言
			if (domainsMap.get(INDEX_ALL[i]) != null) {
				if( domainsMap.get("en")==null){
					continue;
				}
				// 如果已经国际化了该语言，则取当前对象复制给索引对象
				mutil = domainsMap.get(INDEX_ALL[i]);
				if((mutil.getItems()==null || mutil.getItems().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setItems(enMutil.getItems());
				}
				
				if(StringUtils.isBlank(mutil.getTitle()) && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setTitle(enMutil.getTitle());
				}
				
				if(StringUtils.isBlank(mutil.getDesc()) && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setDesc(enMutil.getDesc());
				}
				
				if(StringUtils.isBlank(mutil.getKeywords())  && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setKeywords(enMutil.getKeywords());
				}
				
				if(StringUtils.isBlank(mutil.getMetaDescription())  && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setMetaDescription(enMutil.getMetaDescription());
				}
				
				if(StringUtils.isBlank(mutil.getMetaTitle())  && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setMetaTitle(enMutil.getMetaTitle());
				}
				
				if(StringUtils.isBlank(mutil.getShortDescription())  && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setShortDescription(enMutil.getShortDescription());
				}
				
				if(StringUtils.isBlank(mutil.getMetaKeyword())  && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setMetaKeyword(enMutil.getMetaKeyword());
				}
				
				
				// 和类目匹配
				if((mutil.getProductTypes()==null || mutil.getProductTypes().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setProductTypes(enMutil.getProductTypes());
				}
				
				// 和类目1匹配
				if((mutil.getProductLevel1()==null || mutil.getProductLevel1().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setProductLevel1(enMutil.getProductLevel1());
				}
				
				// 和类目2匹配
				if((mutil.getProductLevel2()==null || mutil.getProductLevel2().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setProductLevel2(enMutil.getProductLevel2());
				}
				
				// 和类目3匹配
				if((mutil.getProductLevel3()==null || mutil.getProductLevel3().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setProductLevel3(enMutil.getProductLevel3());
				}
				
				// 和URL匹配
				if((mutil.getUrl()==null || mutil.getUrl().size()<1)  && !StringUtils.equals(INDEX_ALL[i], "en")){
					MutilLanguage enMutil = domainsMap.get("en");
					mutil.setUrl(enMutil.getUrl());
				}
				//设置语言ID
				Language language = languageMap.get(mutil.getLanguageName());
				if(language!=null){
					mutil.setLanguageId(language.getId());
				}
				
				indexModel.setMutil(mutil);
			} else {
				// 如果没有则取英文站点国际化的属性
				mutil = domainsMap.get("en");
				if (mutil != null) {
					MutilLanguage newMutil = (MutilLanguage) mutil.clone();
					// 设置当前语言
					newMutil.setLanguageName(INDEX_ALL[i]);
					newMutil.setLanguageId(languageMap.get(INDEX_ALL[i]).getId());
					indexModel.setMutil(newMutil);
				}
			}
			
			String indexName = getIndexName(INDEX_ALL[i]);
			if(StringUtils.isNotBlank(indexName)){
				map.put(indexName, indexModel);
			}else {
				throw new Exception ("==========索引数据为空=========="+indexName);
			}
		}
		return map;
	}

	/**
	 * 根据语言解析出不同版本的产品对象
	 * 
	 * @param products
	 * @return
	 */
	public Map<String, List<IndexEntity>> getMutilLanguagesProducts(List<ProductEntity> products) throws Exception {
		if (products == null || products.size() < 1) {
			throw new Exception ("product is null");
		}
		
		Map<String, Language> languageMap = BaseServiceUtil.getLanguageMap();
		Map<String, List<IndexEntity>> map = new HashMap<String, List<IndexEntity>>();
		for (ProductEntity productEntity : products) {
			// 解析product已经国际化的语言
			Map<String, MutilLanguage> domainsMap = getDomains(productEntity);
			//如果没有多语言，该数据丢弃
			if(domainsMap==null || domainsMap.size()<1){
				continue;
			}
			for (int i = 0; i < INDEX_ALL.length; i++) {
				IndexEntity indexModel = new IndexEntity();
				BeanUtils.copyProperties(productEntity, indexModel, "mutil");
				MutilLanguage mutil = null;
				// 判断是否有该语言的国际化属性
				if (domainsMap.get(INDEX_ALL[i]) != null) {
					// 如果已经国际化了该语言，则取当前对象复制给索引对象
					mutil = domainsMap.get(INDEX_ALL[i]);
					if(domainsMap.get("en")==null){
						continue;
					}
					
					if((mutil.getItems()==null || mutil.getItems().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						if(enMutil!=null){
							mutil.setItems(enMutil.getItems());
						}
					}
					
					if(StringUtils.isBlank(mutil.getTitle()) && !StringUtils.equals(INDEX_ALL[i], "en")){
						
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setTitle(enMutil.getTitle());
					}
					
					if(StringUtils.isBlank(mutil.getDesc()) && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setDesc(enMutil.getDesc());
					}
					
					if(StringUtils.isBlank(mutil.getKeywords())  && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setKeywords(enMutil.getKeywords());
					}
					
					if(StringUtils.isBlank(mutil.getMetaDescription())  && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setMetaDescription(enMutil.getMetaDescription());
					}
					
					if(StringUtils.isBlank(mutil.getMetaTitle())  && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setMetaTitle(enMutil.getMetaTitle());
					}
					
					if(StringUtils.isBlank(mutil.getShortDescription())  && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setShortDescription(enMutil.getShortDescription());
					}
					
					if(StringUtils.isBlank(mutil.getMetaKeyword())  && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setMetaKeyword(enMutil.getMetaKeyword());
					}
					
					
					// 和类目匹配
					if((mutil.getProductTypes()==null || mutil.getProductTypes().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setProductTypes(enMutil.getProductTypes());
					}
					
					// 和类目1匹配
					if((mutil.getProductLevel1()==null || mutil.getProductLevel1().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setProductLevel1(enMutil.getProductLevel1());
					}
					
					// 和类目2匹配
					if((mutil.getProductLevel2()==null || mutil.getProductLevel2().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setProductLevel2(enMutil.getProductLevel2());
					}
					
					// 和类目3匹配
					if((mutil.getProductLevel3()==null || mutil.getProductLevel3().size()<1) && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setProductLevel3(enMutil.getProductLevel3());
					}
					
					// 和URL匹配
					if((mutil.getUrl()==null || mutil.getUrl().size()<1)  && !StringUtils.equals(INDEX_ALL[i], "en")){
						MutilLanguage enMutil = domainsMap.get("en");
						mutil.setUrl(enMutil.getUrl());
					}
					//设置语言ID
					Language language = languageMap.get(mutil.getLanguageName());
					if(language!=null){
						mutil.setLanguageId(language.getId());
					}
					
					indexModel.setMutil(mutil);
				}
				else {
					// 如果没有则取英文站点国际化的属性
					mutil = domainsMap.get("en");
					if (mutil != null) {
						MutilLanguage newMutil = (MutilLanguage) mutil.clone();
						// 设置当前语言
						newMutil.setLanguageName(INDEX_ALL[i]);
						newMutil.setLanguageId(languageMap.get(INDEX_ALL[i]).getId());
						indexModel.setMutil(newMutil);
					}
				}
				String indexName = getIndexName(INDEX_ALL[i]);
				// 同类型索引存放在同一集合里面
				List<IndexEntity> oldIndexs = map.get(indexName);
				if (oldIndexs == null) {
					oldIndexs = new ArrayList<IndexEntity>();
				}
				oldIndexs.add(indexModel);
				if(StringUtils.isNotBlank(indexName)){
					map.put(indexName, oldIndexs);
				}else{
					throw new Exception ("==========索引数据为空=========="+indexName);
				}
			}
		}
		return map;
	}


	/**
	 * 解析产品所有已经国际化的语言
	 * 
	 * @param product
	 * @return
	 */
	public Map<String, MutilLanguage> getDomains(ProductEntity product) {
		if (product == null || product.getMutil() == null) {
			try {
				String error_msg = "该数据没有多语言========"+product+"=============";
				FileUtils.writeStringToFile(new File(Config.getValue("error.log.path")+"conversion_index_error.log"), error_msg+"\r\n",true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		List<MutilLanguage> mutils = product.getMutil();
		Map<String, MutilLanguage> map = new HashMap<String, MutilLanguage>();

		for (MutilLanguage mutilLanguage : mutils) {
			String domainName = mutilLanguage.getLanguageName();
			map.put(domainName, mutilLanguage);
		}
		return map;
	}

	/**
	 * 获取索引名称
	 * 
	 * @param languageName
	 *            国家域名缩写
	 * @return
	 */
	public String getIndexName(String languageName) {
		return Constant.ES_INDEX_PREFIX + languageName;
	}

	/**
	 * 获取索引名称
	 * 
	 * @param languageNames
	 *            国家域名缩写集合
	 * @return
	 */
	public List<String> getIndexName(List<String> languageNames) {
		if (languageNames == null || languageNames.size() < 1) {
			return null;
		}
		List<String> languages = new ArrayList<String>();
		for (String languageName : languageNames) {
			String indexName = getIndexName(languageName);
			languages.add(indexName);
		}
		return languages;
	}

	/**
	 * 字符串格式化之后，组装索引名称
	 * 
	 * @param languageName
	 *            语言名称,多个的话以逗号(,)号隔开
	 * @return
	 */
	public List<String> getDefaultIndexName(String languageName) {
		String[] languageNames = languageName.split(",");
		List<String> languages = new ArrayList<String>();
		for (String language : languageNames) {
			String indexName = getIndexName(language);
			languages.add(indexName);
		}
		return languages;
	}

}

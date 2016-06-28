package com.tomtop.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService.ScriptType;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tomtop.BaseClient;
import com.tomtop.common.Config;
import com.tomtop.common.PropertyReader;
import com.tomtop.controller.BaseIndexInfo;
import com.tomtop.entity.Constant;
import com.tomtop.entity.IndexEntity;
import com.tomtop.entity.ProductEntity;
import com.tomtop.entity.ResultEntity;
import com.tomtop.service.IBeforeIndexService;
import com.tomtop.service.index.ISearchIndex;
import com.tomtop.service.index.IndexService;

/**
 * 索引查询
 * 
 * @author ztiny
 * @Date 2015-12-23
 */
@Component
public class BeforeIndexServiceImpl extends BaseIndexInfo implements
		IBeforeIndexService {

	Logger logger = Logger.getLogger(BeforeIndexServiceImpl.class);
	@Resource(name = "indexServiceImpl")
	private IndexService indexServiceImpl;

	@Resource(name = "searchIndexImpl")
	private ISearchIndex searchIndexImpl;

//	private ReentrantLock lock = new ReentrantLock();

	@Override
	public Map<String, Object> insertBulk(String product) {
		String result = "200";
		Map<String, Object> retMap = new HashMap<String, Object>();

		List<ProductEntity> products = null;
		try {
			products = JSON.parseArray(product, ProductEntity.class);
			logger.info(("=====================数据条数记录=====================：" + products
					.size()));

			Map<String, List<IndexEntity>> map = this
					.getMutilLanguagesProducts(products);
			if (map == null || StringUtils.isBlank(product) || map.size() < 8) {
				// 转换错误
				if (map != null && map.size() < 8) {
					throw new Exception("=========map 转换之后大小小于8个========="
							+ map.size());
				} else if (map == null) {
					throw new Exception("=========map 为空=========" + map);
				} else if (product == null || "".equals(product)) {
					throw new Exception("=========map 为空=========" + product);
				}

			}
			for (Iterator<Map.Entry<String, List<IndexEntity>>> it = map
					.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, List<IndexEntity>> entry = it.next();
				String indexName = entry.getKey();
				List<IndexEntity> models = (List<IndexEntity>) entry.getValue();
				result = indexServiceImpl.insertBlukIndex(indexName,
						Constant.ES_INDEX_PRODUCT_TYPE, models);
				retMap.put("result", result);
			}
		} catch (Exception ex) {
			try {
				FileUtils.writeStringToFile(
						new File(Config.getValue("error.log.path")
								+ "conversion_index_error.log"), product
								+ "\r\n", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			retMap.put("result", "500");
			ex.printStackTrace();
		} finally {

		}
		return retMap;
	}

	/**
	 * 修改索引
	 * 
	 * @param product
	 *            json字符串
	 * @return
	 */
	public Map<String, Object> updateIndex(String product) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			ProductEntity entity = JSON.parseObject(product,
					ProductEntity.class);
			// 拆分成一个域名对应一个实体类

			Map<String, IndexEntity> languageIndex = this
					.getMutilLanguagesProduct(entity);
			for (Iterator<Map.Entry<String, IndexEntity>> it = languageIndex
					.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, IndexEntity> entry = it.next();
				String indexName = entry.getKey();
				IndexEntity indexEntity = (IndexEntity) entry.getValue();
				String result = indexServiceImpl.updateIndex(indexName,
						Constant.ES_INDEX_PRODUCT_TYPE, indexEntity);
				retMap.put(indexName, result);
			}
		} catch (Exception e) {
			retMap.clear();
			retMap.put("result", "修改索引程序内部错误");
			e.printStackTrace();
		}
		return retMap;
	}

	/**
	 * 创建索引
	 * 
	 * @param product
	 *            json字符串
	 */
	@Override
	public Map<String, Object> insert(String prod) {
		String result = "200";
		Map<String, Object> map = new HashMap<String, Object>();
		ProductEntity product = JSON.parseObject(prod, ProductEntity.class);
		try {
			// 拆分成一个域名对应一个实体类
			Map<String, IndexEntity> languageIndex = this
					.getMutilLanguagesProduct(product);
			for (Iterator<Map.Entry<String, IndexEntity>> it = languageIndex
					.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, IndexEntity> entry = it.next();
				String indexName = entry.getKey();
				IndexEntity indexEntity = (IndexEntity) entry.getValue();
				result = indexServiceImpl.insertIndex(indexName,
						Constant.ES_INDEX_PRODUCT_TYPE, indexEntity);
				map.put("result", result);
			}
		} catch (Exception ex) {
			try {
				String msg = "insert  failure==================================="
						+ prod;
				org.apache.commons.io.FileUtils
						.writeStringToFile(
								new File(Config.getValue("error.log.path")
										+ "create_index_error.log"), msg
										+ "\r\n", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			map.put("result", "500");
			ex.printStackTrace();
		}
		return map;
	}

	/**
	 * 删除索引
	 * 
	 * @param type
	 *            (状态为1和2，1代表根据根据lingingId删除,2代表根据sku删除)
	 * @param language
	 *            语言,国家域名缩写
	 * @param map
	 *            <删除的健,删除的值>
	 * @return
	 */
	@Override
	public Map<String, Object> deleteIndex(int type, String language,
			Map<String, Object> map) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			// 组装索引
			List<String> indexNames = getDefaultIndexName(language);
			// 遍历多个索引进行删除数据
			for (String indexName : indexNames) {
				String result = indexServiceImpl.deleteIndex(indexName,
						Constant.ES_INDEX_PRODUCT_TYPE, map);
				retMap.put(indexName, result);
			}
		} catch (Exception ex) {
			retMap.clear();
			retMap.put("result", "删除索引程序内部错误");
			ex.printStackTrace();
		}
		return retMap;
	}

	@Override
	public Map<String, Object> updateIndexPart(String language,
			String listingid, String script) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<ResultEntity> results = new ArrayList<ResultEntity>();
		try {
			if (StringUtils.equals(language, "all")) {
				language = PropertyReader.getValue(Constant.ES_CONFIG_PATH,
						Constant.ES_CONFIG_PRODUCT_INDEX_ALL_KEY);
			}
			List<String> indexNames = this.getDefaultIndexName(language);
			for (String string : indexNames) {
				indexServiceImpl.update(string, Constant.ES_INDEX_PRODUCT_TYPE,
						listingid, script);
				ResultEntity result = new ResultEntity(string, "200");
				results.add(result);
			}
		} catch (Exception ex) {
			ResultEntity result = new ResultEntity("failure", "500");
			results.add(result);
			ex.printStackTrace();
		}
		retMap.put("result", results);
		return retMap;
	}

	@Override
	public boolean updateIndex(String language, String listingid,
			Map<String, Object> map) {


		UpdateRequest updateRequest = null;
		JSONObject json = new JSONObject();
		try {
			String index = Constant.ES_INDEX_PREFIX + language;
			String type = Constant.ES_INDEX_PRODUCT_TYPE;
			Client client = BaseClient.getIndexClient(index, type);

			updateRequest = new UpdateRequest(index, type, listingid);

			Iterator<String> iterator = map.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();

				Object value = map.get(key);

				String[] keys = key.split("\\.");

				if (keys.length == 1) {
					json.put(key, value);
				} else {
					JSONObject currentEle = json.getJSONObject(keys[0]);
					if (currentEle == null) {
						currentEle = new JSONObject();
						json.put(keys[0], currentEle);
					}
					for (int i = 1; i < keys.length - 1; i++) {
						JSONObject ele = currentEle.getJSONObject(keys[i]);
						if (ele == null) {
							ele = new JSONObject();
							currentEle.put(keys[i], ele);
						}

						currentEle = ele;
					}
					currentEle.put(keys[keys.length - 1], value);

				}

			}
			updateRequest.doc(json);

			client.update(updateRequest).get();
			return true;
		} catch (Exception e) {
			logger.error("update json:" + json.toJSONString());
			logger.error("update index error", e);
			return false;
		} 
	}

	/**
	 * 用Script局部更新索引
	 * 
	 * @author lijun
	 * @param language
	 * @param listingid
	 * @param map
	 * @return
	 */
	public boolean updateIndexByScript(String language, String listingid,
			Map<String, Object> map) {

		if (map == null) {
			throw new NullPointerException("map is null");
		}
		try {

			String index = Constant.ES_INDEX_PREFIX + language;
			String type = Constant.ES_INDEX_PRODUCT_TYPE;
			Client client = BaseClient.getIndexClient(index, type);

			UpdateRequest updateRequest = new UpdateRequest(index, type,
					listingid);

			Iterator<Entry<String, Object>> iterator = map.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				Script script = createScript(entry.getKey(), entry.getValue());
				updateRequest.script(script);
			}

			client.update(updateRequest).get();
			return true;
		} catch (Exception e) {
			this.logger.error("update index by script error", e);
			return false;
		}
	}

	private Script createScript(String field, Object value) {

		Map<String, Object> paras = Maps.newHashMap();
		paras.put("field", field);
		paras.put("plusVal", value);

		Script script = new Script("plus", ScriptType.FILE, LANG_GROOVY, paras);

		return script;

	}
}

package com.tomtop.management.shipping.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.tomtop.management.common.Page;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.base.model.Storage;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.shipping.model.ShippingType;
import com.tomtop.management.shipping.values.ShippingFreight;
import com.tomtop.management.shipping.values.ShippingFreightData;

@Service
public class ShippingFreightChargeService {

	@Autowired
	GlobalParameter parameter;
	
	/**
	 * 
	 * @Title: freightCalculate
	 * @Description: TODO(运费计算)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param sf
	 * @param @param sort
	 * @param @return    
	 * @return Page<ShippingFreight>    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月12日
	 */
	public Page<ShippingFreight> freightCalculate(int pageNo, int pageLimit, ShippingFreight sf, String sort) {
		Country c = Country.db().find(Country.class, sf.getCountryId());
		sf.setCountry(c.getIso_code_two());
		List<ShippingFreight> sfList = getSFList(sf);
		Page<ShippingFreight> sfPage = new Page<ShippingFreight>();
		List<ShippingFreight> sfPageList = new ArrayList<ShippingFreight>();
		if (null != sfList && sfList.size() > 0) {
			// 排序
			if ("asc".equals(sort)) {
				Collections.sort(sfList, new Comparator<ShippingFreight>() {
					public int compare(ShippingFreight sf0, ShippingFreight sf1) {
						return sf0.getPrice().compareTo(sf1.getPrice());
					}
				});
			}
			if ("desc".equals(sort)) {
				Collections.sort(sfList, new Comparator<ShippingFreight>() {
					public int compare(ShippingFreight sf0, ShippingFreight sf1) {
						return sf1.getPrice().compareTo(sf0.getPrice());
					}
				});
			}
			// 分页
			int startIndex = pageLimit * (pageNo - 1);
			int endIndex = pageLimit * pageNo;
			if (endIndex > sfList.size()) {
				endIndex = sfList.size();
			}
			for (int i = startIndex; i < endIndex; i++) {
				sfPageList.add(sfList.get(i));
			}
			// 设置仓库名称和模版名称
			List<Long> storageIds = new ArrayList<Long>();
			List<Long> shipppingTypeIds = new ArrayList<Long>();
			for (ShippingFreight shf : sfList) {
				storageIds.add(shf.getStorageNameId());
				shipppingTypeIds.add(shf.getShippingTypeId());
			}
			List<Storage> storages = Storage.db().find(Storage.class).where().in("iid", storageIds).findList();
			List<ShippingType> shippingTypes = ShippingType.db().find(ShippingType.class).where()
					.in("id", shipppingTypeIds).findList();
			Map<Long, Storage> storageMap = Maps.uniqueIndex(storages, st -> st.getIid());
			Map<Long, ShippingType> shippingTypeMap = Maps.uniqueIndex(shippingTypes, st -> st.getId());
			for (ShippingFreight shf : sfList) {
				shf.setCountry(c.getName());
				if (null != storageMap.get(shf.getStorageNameId())) {
					shf.setStorageName(storageMap.get(shf.getStorageNameId()).getCstoragename());
				}
				if (null != shippingTypeMap.get(shf.getShippingTypeId())) {
					shf.setShippingTypeName(shippingTypeMap.get(shf.getShippingTypeId()).getType_name());
				}
			}
			sfPage.setCount(sfList.size());
		} else {
			sfPage.setCount(0);
		}
		sfPage.setLimit(pageLimit);
		sfPage.setPageNo(pageNo);
		sfPage.setList(sfPageList);
		return sfPage;
	}

	/**
	 * 
	 * @Title: getSFList
	 * @Description: TODO(获取运费计算结果集)
	 * @param @param sf
	 * @param @return    
	 * @return List<ShippingFreight>    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月12日
	 */
	private List<ShippingFreight> getSFList(ShippingFreight sf) {
		String url = parameter.getShippingFreightUrl();
		String token = parameter.getShippingFreightToken();
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("token", token);
		// 创建参数
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("country", sf.getCountry());
		jsonParam.put("weight", sf.getWeight());
		jsonParam.put("storageId", sf.getStorageNameId());
		jsonParam.put("shippingTypeId", sf.getShippingTypeId());
		List<ShippingFreight> sfList = new ArrayList<ShippingFreight>();
		ObjectMapper om = new ObjectMapper();
		StringEntity reqentity = new StringEntity(jsonParam.toString(), "utf-8");
		try {
			httppost.setEntity(reqentity);
			// 发送请求
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String s = EntityUtils.toString(entity, "UTF-8");
					ShippingFreightData sfd = om.readValue(s, ShippingFreightData.class);
					sfList = sfd.getData();
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sfList;
	}

}

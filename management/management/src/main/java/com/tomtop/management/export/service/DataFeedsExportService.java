package com.tomtop.management.export.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.tomtop.management.common.Page;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.PageData;
import com.tomtop.management.export.data.model.ProductData;
import com.tomtop.management.services.CategoryService;
import com.tomtop.management.services.ProductService;

@Service
public class DataFeedsExportService {

	@Autowired
	CategoryService categoryService;

	@Autowired
	GlobalParameter parameter;

	@Autowired
	ProductService pService;

	public List<BaseData> getData(Integer categoryId, Integer language_id, String currency_code, int pageLimit,
			Integer isExportFreight, Long storageId, String country) {
		ArrayList<BaseData> bList = new ArrayList<BaseData>();
		NetHttpTransport transport = new NetHttpTransport();
		HttpRequestFactory httpRequestFactory = transport.createRequestFactory();
		List<Integer> categories = categoryService.getCategoryIdsByParentId(categoryId);
		try {
			GenericUrl url = new GenericUrl(
					parameter.getSearchUrl() + "?size=" + pageLimit + "&categoryId=" + categoryId + "&currency="
							+ currency_code + "&lang=" + language_id + "&website=" + pService.getCurrentSiteId());
			String response = httpRequestFactory.buildGetRequest(url).execute().parseAsString();
			JSONObject json = JSONObject.parseObject(response);
			if (1 == json.getInteger("ret")) {
				JSONObject jj = json.getJSONObject("data");
				String date = jj.getString("rpdbos");
				String page = jj.getString("page");
				PageData pageData = new ObjectMapper().readValue(page, PageData.class);
				for (int i = 2; i <= pageData.getTotalPage(); i++) {
					bList.addAll(getBaseList(categoryId, language_id, currency_code, categories, i, pageLimit,
							isExportFreight, storageId, country));
				}

				System.out.println("subBase" + bList.size());
				List<ProductData> pdList = new ObjectMapper().readValue(date, new TypeReference<List<ProductData>>() {
				});
				System.out.println("product1" + pdList.size());
				for (ProductData pData : pdList) {
					BaseData bData = new BaseData();
					bData.setCurrency_code(pData.getCurrencyCode());
					bData.setDescription(pData.getDesc());
					bData.setPictureUrl("http://img.tomtop-cdn.com/product/xy/500/500/" + pData.getProductImage());
					bData.setUrl("http://www.tomtop.com/" + pData.getProductUrl() + ".html");
					bData.setBrand(pData.getBrand());
					bData.setSku(pData.getSku());
					bData.setStatus(pData.getStatus().toString());
					bData.setShortDescription(pData.getShortDesc());
					bData.setSearchTerms(pData.getSearchTerms());
					bData.setTopseller(pData.getTopseller());
					bData.setTitle(pData.getTitle());
					bData.setPrice(pData.getPrice());
					bData.setOldPrice(pData.getOldprice());
					if (null != categories && categories.size() > 0) {
						for (int cid : pData.getCategoryIds()) {
							if (categories.contains(cid)) {
								bData.setCategoryId(cid);
								break;
							}
						}
					}
					bData.setParentCategory(categoryId);
					bData.setIsExportFreight(isExportFreight);
					bData.setStorageId(storageId);
					bData.setCountry(country);
					bList.add(bData);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bList;
	}

	private ArrayList<BaseData> getBaseList(Integer categoryId, Integer language_id, String currency_code,
			List<Integer> categories, int page, int pageLimit, Integer isExportFreight, Long storageId, String country)
					throws IOException {
		ArrayList<BaseData> bList = new ArrayList<BaseData>();
		NetHttpTransport transport = new NetHttpTransport();
		HttpRequestFactory httpRequestFactory = transport.createRequestFactory();
		GenericUrl url = new GenericUrl(parameter.getSearchUrl() + "?size=" + pageLimit + "&categoryId=" + categoryId
				+ "&currency=" + currency_code + "&lang=" + language_id + "&website=" + pService.getCurrentSiteId()
				+ "&page=" + page);
		String response = httpRequestFactory.buildGetRequest(url).execute().parseAsString();
		JSONObject json = JSONObject.parseObject(response);
		if (1 == json.getInteger("ret")) {
			JSONObject jj = json.getJSONObject("data");
			String date = jj.getString("rpdbos");
			List<ProductData> pdList = new ObjectMapper().readValue(date, new TypeReference<List<ProductData>>() {
			});
			if (pdList != null && pdList.size() > 0) {
				for (int i = 0; i < pdList.size(); i++) {
					ProductData pd = pdList.get(i);
					BaseData bData = new BaseData();
					bData.setCurrency_code(pd.getCurrencyCode());
					bData.setDescription(pd.getDesc());
					bData.setPictureUrl("http://img.tomtop-cdn.com/product/xy/500/500/" + pd.getProductImage());
					bData.setUrl("http://www.tomtop.com/" + pd.getProductUrl() + ".html");
					bData.setBrand(pd.getBrand());
					bData.setSku(pd.getSku());
					bData.setStatus(pd.getStatus().toString());
					bData.setShortDescription(pd.getShortDesc());
					bData.setSearchTerms(pd.getSearchTerms());
					bData.setTopseller(pd.getTopseller());
					bData.setTitle(pd.getTitle());
					bData.setPrice(pd.getPrice());
					bData.setOldPrice(pd.getOldprice());
					if (null != categories && categories.size() > 0) {
						for (int cid : pd.getCategoryIds()) {
							if (categories.contains(cid)) {
								bData.setCategoryId(cid);
								break;
							}
						}
					}
					bData.setParentCategory(categoryId);
					bData.setIsExportFreight(isExportFreight);
					bData.setStorageId(storageId);
					bData.setCountry(country);
					bList.add(bData);
				}
			}
		}
		return bList;
	}

	public Page<BaseData> getFilePage(int pageNo, int pageLimit, int categoryId) throws IOException {
		List<Integer> categories = categoryService.getCategoryIdsByParentId(categoryId);
		Page<BaseData> basePage = new Page<BaseData>();
		List<BaseData> bDatas = getBaseList(categoryId, 1, "USD", categories, pageNo, pageLimit, null, null, null);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		for (BaseData bData : bDatas) {
			if (null != bData.getCategoryId()) {
				Category category = Category.db().find(Category.class).where().eq("icategoryid", bData.getCategoryId())
						.eq("iwebsiteid", pService.getCurrentSiteId()).findUnique();
				if (null != category) {
					bData.setCategoryName(category.getCpath());
				}
			}
			bData.setStatus("in Stock");
			baseDatas.add(bData);

		}
		NetHttpTransport transport = new NetHttpTransport();
		HttpRequestFactory httpRequestFactory = transport.createRequestFactory();
		GenericUrl url = new GenericUrl(parameter.getSearchUrl() + "?size=" + pageLimit + "&categoryId=" + categoryId
				+ "&lang=" + 1 + "&website=" + pService.getCurrentSiteId() + "&page=" + pageNo);
		String response = httpRequestFactory.buildGetRequest(url).execute().parseAsString();
		JSONObject json = JSONObject.parseObject(response);
		if (1 == json.getInteger("ret")) {
			JSONObject jj = json.getJSONObject("data");
			String page = jj.getString("page");
			PageData pageData = new ObjectMapper().readValue(page, PageData.class);
			basePage.setCount(pageData.getTotalRecord());
			basePage.setPageNo(pageNo);
			basePage.setLimit(pageData.getPageSize());
			basePage.setList(baseDatas);
		}
		return basePage;
	}

	/**
	 * 生成.zip文件;
	 * 
	 * @param path
	 * @throws IOException
	 */
	public File craeteZipPath(String path) throws IOException {
		ZipOutputStream zipOutputStream = null;
		File file = new File(path + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip");
		zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		File files = new File(path);
		FileInputStream fileInputStream = null;
		byte[] buf = new byte[1024];
		int len = 0;
		if (files != null) {
			String fileName = files.getName();
			fileInputStream = new FileInputStream(files);
			// 放入压缩zip包中;
			zipOutputStream.putNextEntry(new ZipEntry(fileName));

			// 读取文件;
			while ((len = fileInputStream.read(buf)) > 0) {
				zipOutputStream.write(buf, 0, len);
			}
			// 关闭;
			zipOutputStream.closeEntry();
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}

		if (zipOutputStream != null) {
			zipOutputStream.close();
		}
		return file;
	}

	/**
	 * 删除目录下所有的文件;
	 * 
	 * @param path
	 */
	public void deleteExcelPath(File file) {
		try {
			String[] files = null;
			if (file != null) {
				files = file.list();
			}

			if (file.isDirectory()) {
				for (int i = 0; i < files.length; i++) {
					deleteExcelPath(new File(file, files[i]));
				}
			} else {
				file.delete();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

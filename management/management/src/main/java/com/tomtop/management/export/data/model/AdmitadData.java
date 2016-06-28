package com.tomtop.management.export.data.model;

import java.util.List;

import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;

/**
 * 
    * @ClassName: AdmitadData
    * @Description: 大类
    * @author liuxin
    * @date 2016年3月14日
    *
 */
@ExportFileColumn(name = "shop", priority = 0)
public class AdmitadData implements IData {
	@ExportFileColumn(name = "name", priority = 1)
	private String name;
	@ExportFileColumn(name = "company", priority = 2)
	private String company;
	@ExportFileColumn(name = "url", priority = 3)
	private String url;
	@ExportFileColumn(name = "currencies", priority = 4)
	private List<CurrencyData> currencyDatas;
	@ExportFileColumn(name = "categories", priority = 5)
	private List<CategoryAttrData> category;
	@ExportFileColumn(name = "offers", priority = 6)
	private List<ProductDetailData> productDetails;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<CurrencyData> getCurrencyDatas() {
		return currencyDatas;
	}

	public void setCurrencyDatas(List<CurrencyData> currencyDatas) {
		this.currencyDatas = currencyDatas;
	}

	public List<ProductDetailData> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetailData> productDetails) {
		this.productDetails = productDetails;
	}

	public List<CategoryAttrData> getCategory() {
		return category;
	}

	public void setCategory(List<CategoryAttrData> category) {
		this.category = category;
	}
	
	

}

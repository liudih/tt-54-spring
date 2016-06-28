package com.tomtop.management.export.data.model;

import com.tomtop.management.ebean.product.model.ProductModel;
import com.tomtop.management.export.api.IData;

public class BaseData extends ProductModel implements IData {
	private String url;// 产品url
	private String price;// 现价
	private String oldPrice;// 原价
	private String pictureUrl;// 图片url
	private Integer categoryId;// 品类id
	private String title;// 产品名称title
	private String vendor;// 产品制造商
	private String description;// 产品描述
	private String typePrefix;// 产品类别
	private String model;// 产品型号
	private String sku;//
	private Integer language_id;// 语言
	private String currency_code;// 货币
	private String plat_form;// 平台
	private String file_type;// 导出类型
	private String status;// 状态
	private String brand;// 品牌
	private String shortDescription;// 短描述
	private String searchTerms; // 关键字
	private Boolean topseller;// 是否热卖
	private Integer parentCategory;// 品类条件
	private String categoryName;
	private Integer isExportFreight;//是否导出运费
	private Long storageId;//仓库
	private String country;//国家

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypePrefix() {
		return typePrefix;
	}

	public void setTypePrefix(String typePrefix) {
		this.typePrefix = typePrefix;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getPlat_form() {
		return plat_form;
	}

	public void setPlat_form(String plat_form) {
		this.plat_form = plat_form;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public Boolean getTopseller() {
		return topseller;
	}

	public void setTopseller(Boolean topseller) {
		this.topseller = topseller;
	}

	public Integer getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Integer parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getIsExportFreight() {
		return isExportFreight;
	}

	public void setIsExportFreight(Integer isExportFreight) {
		this.isExportFreight = isExportFreight;
	}

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}

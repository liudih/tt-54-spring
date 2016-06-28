package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;

public class FaceBookData implements IData {
	@ExportFileColumn(name = "id", priority = 1)
	private String sku;// 产品SKU

	@ExportFileColumn(name = "title", priority = 1)
	private String name;// 产品名称

	@ExportFileColumn(name = "description", priority = 1)
	private String description;// 产品描述

	@ExportFileColumn(name = "condition", priority = 1)
	private String condition;// 产品状态，固定填New

	@ExportFileColumn(name = "price", priority = 1)
	private String price;// 产品价格

	@ExportFileColumn(name = "availability", priority = 1)
	private String availability;// 固定填In stock

	@ExportFileColumn(name = "link", priority = 1)
	private String productUrl;// 产品URL

	@ExportFileColumn(name = "image link", priority = 1)
	private String imgUrl;// 产品图片URL

	@ExportFileColumn(name = "mpn", priority = 1)
	private String mpn;// 产品SKU

	@ExportFileColumn(name = "brand", priority = 1)
	private String brand;// 产品品牌

	@ExportFileColumn(name = "google product category", priority = 1)
	private String googleCategory;// 对应的Google那边的类目，先留空

	@ExportFileColumn(name = "productType", priority = 8)
	private String productType;// 我们自己的网站类目

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMpn() {
		return mpn;
	}

	public void setMpn(String mpn) {
		this.mpn = mpn;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getGoogleCategory() {
		return googleCategory;
	}

	public void setGoogleCategory(String googleCategory) {
		this.googleCategory = googleCategory;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}

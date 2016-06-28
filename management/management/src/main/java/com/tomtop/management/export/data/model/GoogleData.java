package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;

public class GoogleData implements IData {
	@ExportFileColumn(name = "id", priority = 1)
	private String id;// 产品SKU

	@ExportFileColumn(name = "title", priority = 2)
	private String title;// 产品名称

	@ExportFileColumn(name = "description", priority = 3)
	private String description;// 产品描述

	@ExportFileColumn(name = "condition", priority = 4)
	private String condition;// 产品状态，固定填New

	@ExportFileColumn(name = "price", priority = 5)
	private String price;// 产品价格

	@ExportFileColumn(name = "availability", priority = 6)
	private String availability;// 固定填In stock

	@ExportFileColumn(name = "link", priority = 7)
	private String link;// 产品URL

	@ExportFileColumn(name = "img link", priority = 8)
	private String imgUrl;// 产品图片URL(image link)

	@ExportFileColumn(name = "mpn", priority = 9)
	private String mpn;// 产品SKU

	@ExportFileColumn(name = "brand", priority = 10)
	private String brand;// 产品品牌

	@ExportFileColumn(name = "googleCategory", priority = 11)
	private String googleCategory;// 对应的Google那边的类目，先留空(google product category)

	@ExportFileColumn(name = "productType", priority = 12)
	private String productType;// 我们自己的网站类目(product type)

	@ExportFileColumn(name = "shipping", priority = 13)
	private String shipping;// 物流运费

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

}

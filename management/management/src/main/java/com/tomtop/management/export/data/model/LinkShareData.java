package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;

public class LinkShareData implements IData {
	@ExportFileColumn(name = "id", priority = 1)
	private String id;// 商品SKU
	@ExportFileColumn(name = "title", priority = 2)
	private String title;// 商品名称
	@ExportFileColumn(name = "description", priority = 3)
	private String description;// 商品描述
	@ExportFileColumn(name = "condition", priority = 4)
	private String condition;// 状态，固定填New
	@ExportFileColumn(name = "price", priority = 5)
	private String price;// 商品价格
	@ExportFileColumn(name = "availability", priority = 6)
	private String availability;// 固定填in stock
	@ExportFileColumn(name = "link", priority = 7)
	private String link;// 商品URL
	@ExportFileColumn(name = "imageLink", priority = 8)
	private String imageLink;// 商品图片URL
	@ExportFileColumn(name = "mpn", priority = 9)
	private String mpn;// 商品SKU
	@ExportFileColumn(name = "brand", priority = 10)
	private String brand;// 商品品牌
	@ExportFileColumn(name = "googleCategory", priority = 11)
	private String googleCategory;// 对应Google品类，先留空
	@ExportFileColumn(name = "productType", priority = 12)
	private String productType;// 商品类目，我们自己网站的类目
	@ExportFileColumn(name = "customLabel0", priority = 13)
	private String customLabel0;
	@ExportFileColumn(name = "customLabel1", priority = 14)
	private String customLabel1;// 价格区间
	@ExportFileColumn(name = "gender", priority = 15)
	private String gender;
	@ExportFileColumn(name = "ageGroup", priority = 16)
	private String ageGroup;
	@ExportFileColumn(name = "size", priority = 17)
	private String size;
	@ExportFileColumn(name = "color", priority = 28)
	private String color;
	@ExportFileColumn(name = "shipping", priority = 19)
	private String shipping;

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

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
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

	public String getCustomLabel0() {
		return customLabel0;
	}

	public void setCustomLabel0(String customLabel0) {
		this.customLabel0 = customLabel0;
	}

	public String getCustomLabel1() {
		return customLabel1;
	}

	public void setCustomLabel1(String customLabel1) {
		this.customLabel1 = customLabel1;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}

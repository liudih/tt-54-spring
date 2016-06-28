package com.tomtop.management.export.data.model;

import java.io.Serializable;
import java.util.List;

public class ProductData implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 4053065535671032374L;
	private String productUrl;// 产品URL地址
	private String price; // 现价
	private String oldprice; // 原价
	private String productImage; // 产品图片
	private List<Integer> categoryIds;
	private String currencyCode;
	private String title;// 产品标题
	private String desc;// 产品描述
	private String sku;
	private Boolean topseller;// 是否热卖,有hot标签的
	private String brand;// 品牌名字,没有的话为空
	private String searchTerms; // 关键字，没有的话就写产品标题/名称
	private String shortDesc; // 简要描述
	private Integer status; // 状态

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOldprice() {
		return oldprice;
	}

	public void setOldprice(String oldprice) {
		this.oldprice = oldprice;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<Integer> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryId(List<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getTopseller() {
		return topseller;
	}

	public void setTopseller(Boolean topseller) {
		this.topseller = topseller;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

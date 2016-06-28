package com.tomtop.product.models.vo;


/**
 * 促销价
 * 
 * @author ztiny
 * @String 2015-12-21
 */
public class PromotionPrice {

	// 商品SKU
	private String sku;
	// 站点
	private Integer webSiteId;

	// 促销价格
	private Double price;
	// 开始时间
	private String beginDate;
	// 结束时间
	private String endDate;

	public PromotionPrice() {

	}

	public PromotionPrice(String sku, Integer websites, Double price,
			String beginDate, String endDate) {
		this.sku = sku;
		this.webSiteId = websites;
		this.price = price;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getWebSiteId() {
		return webSiteId;
	}

	public void setWebSites(Integer webSites) {
		this.webSiteId = webSites;
	}

}

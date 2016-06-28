package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;

public class ClixgaloreData implements IData {
	@ExportFileColumn(name = "title", priority = 1)
	private String title;
	@ExportFileColumn(name = "currency", priority = 2)
	private String currency;
	@ExportFileColumn(name = "baseprice", priority = 3)
	private String baseprice;
	@ExportFileColumn(name = "recommendPrice", priority = 4)
	private double recommendPrice;
	@ExportFileColumn(name = "salePrice", priority = 5)
	private double salePrice;
	@ExportFileColumn(name = "cost", priority = 6)
	private double cost;
	@ExportFileColumn(name = "brandName", priority = 7)
	private String brandName;
	@ExportFileColumn(name = "briefDes", priority = 8)
	private String briefDes;
	@ExportFileColumn(name = "description", priority = 9)
	private String description;
	@ExportFileColumn(name = "url", priority = 10)
	private String url;
	@ExportFileColumn(name = "imgUrl", priority = 11)
	private String imgUrl;
	@ExportFileColumn(name = "imgThumbnalUrl", priority = 12)
	private String imgThumbnalUrl;
	@ExportFileColumn(name = "firstCategory", priority = 8)
	private String firstCategory;
	@ExportFileColumn(name = "secoundCategory", priority = 9)
	private String secoundCategory;
	@ExportFileColumn(name = "thirdCategory", priority = 10)
	private String thirdCategory;
	@ExportFileColumn(name = "season", priority = 14)
	private String season;
	@ExportFileColumn(name = "gender", priority = 15)
	private String gender;
	@ExportFileColumn(name = "availability", priority = 16)
	private Integer availability;
	@ExportFileColumn(name = "size", priority = 17)
	private String size;
	@ExportFileColumn(name = "color", priority = 18)
	private String color;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBaseprice() {
		return baseprice;
	}

	public void setBaseprice(String baseprice) {
		this.baseprice = baseprice;
	}

	public double getRecommendPrice() {
		return recommendPrice;
	}

	public void setRecommendPrice(double recommendPrice) {
		this.recommendPrice = recommendPrice;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBriefDes() {
		return briefDes;
	}

	public void setBriefDes(String briefDes) {
		this.briefDes = briefDes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgThumbnalUrl() {
		return imgThumbnalUrl;
	}

	public void setImgThumbnalUrl(String imgThumbnalUrl) {
		this.imgThumbnalUrl = imgThumbnalUrl;
	}

	public String getFirstCategory() {
		return firstCategory;
	}

	public void setFirstCategory(String firstCategory) {
		this.firstCategory = firstCategory;
	}

	public String getSecoundCategory() {
		return secoundCategory;
	}

	public void setSecoundCategory(String secoundCategory) {
		this.secoundCategory = secoundCategory;
	}

	public String getThirdCategory() {
		return thirdCategory;
	}

	public void setThirdCategory(String thirdCategory) {
		this.thirdCategory = thirdCategory;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
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

}

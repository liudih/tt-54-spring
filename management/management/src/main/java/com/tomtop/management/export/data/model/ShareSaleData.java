package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;

public class ShareSaleData implements IData {

	@ExportFileColumn(name = "sku", priority = 1)
	private String sku;// 产品SKU

	@ExportFileColumn(name = "name", priority = 2)
	private String name;// 产品名称

	@ExportFileColumn(name = "productUrl", priority = 3)
	private String productUrl;// 产品URL

	@ExportFileColumn(name = "price", priority = 4)
	private String price;// 产品价格

	@ExportFileColumn(name = "retailPrice", priority = 5)
	private String retailPrice;// 零售产品价格

	@ExportFileColumn(name = "imgUrl", priority = 6)
	private String imgUrl;// 产品图片URL

	@ExportFileColumn(name = "thumbnailImageUrl", priority = 7)
	private String thumbnailImageUrl;// 产品缩略图URL

	@ExportFileColumn(name = "commission", priority = 8)
	private double commission;// 产品价格*0.07

	@ExportFileColumn(name = "category", priority = 9)
	private String category;// 类目ID，见附件类目对应参照表《website-categories》

	@ExportFileColumn(name = "subCategory", priority = 10)
	private String subCategory;// 子类目ID，见附件类目对应参照表《website-categories》

	@ExportFileColumn(name = "description", priority = 11)
	private String description;// 描述，加<br/>

	@ExportFileColumn(name = "searchTerms", priority = 12)
	private String searchTerms;// 关键字，没有的话就写产品标题/名称

	@ExportFileColumn(name = "status", priority = 13)
	private String status;// 状态，填in stock

	@ExportFileColumn(name = "merchantID", priority = 14)
	private String merchantID;// 我们tomtop的ID，固定填27868

	@ExportFileColumn(name = "custom1", priority = 15)
	private String custom1;

	@ExportFileColumn(name = "custom2", priority = 16)
	private String custom2;

	@ExportFileColumn(name = "custom3", priority = 17)
	private String custom3;

	@ExportFileColumn(name = "custom4", priority = 18)
	private String custom4;

	@ExportFileColumn(name = "custom5", priority = 19)
	private String custom5;

	@ExportFileColumn(name = "manufacturer", priority = 20)
	private String manufacturer;

	@ExportFileColumn(name = "partNumber", priority = 21)
	private String partNumber;

	@ExportFileColumn(name = "firstCategory", priority = 22)
	private String firstCategory;

	@ExportFileColumn(name = "secoundCategory", priority = 23)
	private String secoundCategory;

	@ExportFileColumn(name = "shortDescription", priority = 25)
	private String shortDescription;// 简短描述

	@ExportFileColumn(name = "isbn", priority = 26)
	private String isbn;

	@ExportFileColumn(name = "upc", priority = 27)
	private String upc;

	@ExportFileColumn(name = "crossSell", priority = 28)
	private String crossSell;

	@ExportFileColumn(name = "merchantGroup", priority = 29)
	private String merchantGroup;

	@ExportFileColumn(name = "merchantSubgroup", priority = 31)
	private String merchantSubgroup;

	@ExportFileColumn(name = "compatibleWith", priority = 32)
	private String compatibleWith;

	@ExportFileColumn(name = "compareTo", priority = 33)
	private String compareTo;

	@ExportFileColumn(name = "quantityDiscount", priority = 34)
	private String quantityDiscount;

	@ExportFileColumn(name = "bestseller", priority = 35)
	private String bestseller;

	@ExportFileColumn(name = "addToCartURL", priority = 36)
	private String addToCartURL;

	@ExportFileColumn(name = "reviewsRSSURL", priority = 37)
	private String reviewsRSSURL;

	@ExportFileColumn(name = "option1", priority = 38)
	private String option1;

	@ExportFileColumn(name = "option2", priority = 39)
	private String option2;

	@ExportFileColumn(name = "option3", priority = 40)
	private String option3;

	@ExportFileColumn(name = "option4", priority = 41)
	private String option4;

	@ExportFileColumn(name = "option5", priority = 42)
	private String option5;

	@ExportFileColumn(name = "customCommissions", priority = 43)
	private String customCommissions;

	@ExportFileColumn(name = "customCommissionIsFlatRate", priority = 44)
	private String customCommissionIsFlatRate;

	@ExportFileColumn(name = "customCommissionNewCustomerMultiplier", priority = 45)
	private String customCommissionNewCustomerMultiplier;

	@ExportFileColumn(name = "mobileURL", priority = 46)
	private String mobileURL;

	@ExportFileColumn(name = "mobileImage", priority = 47)
	private String mobileImage;

	@ExportFileColumn(name = "mobileThumbnail", priority = 48)
	private String mobileThumbnail;

	@ExportFileColumn(name = "reservedForFutureUse", priority = 49)
	private String reservedForFutureUse;

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

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}

	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getCustom1() {
		return custom1;
	}

	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}

	public String getCustom2() {
		return custom2;
	}

	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}

	public String getCustom3() {
		return custom3;
	}

	public void setCustom3(String custom3) {
		this.custom3 = custom3;
	}

	public String getCustom4() {
		return custom4;
	}

	public void setCustom4(String custom4) {
		this.custom4 = custom4;
	}

	public String getCustom5() {
		return custom5;
	}

	public void setCustom5(String custom5) {
		this.custom5 = custom5;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getCrossSell() {
		return crossSell;
	}

	public void setCrossSell(String crossSell) {
		this.crossSell = crossSell;
	}

	public String getMerchantGroup() {
		return merchantGroup;
	}

	public void setMerchantGroup(String merchantGroup) {
		this.merchantGroup = merchantGroup;
	}

	public String getMerchantSubgroup() {
		return merchantSubgroup;
	}

	public void setMerchantSubgroup(String merchantSubgroup) {
		this.merchantSubgroup = merchantSubgroup;
	}

	public String getCompatibleWith() {
		return compatibleWith;
	}

	public void setCompatibleWith(String compatibleWith) {
		this.compatibleWith = compatibleWith;
	}

	public String getCompareTo() {
		return compareTo;
	}

	public void setCompareTo(String compareTo) {
		this.compareTo = compareTo;
	}

	public String getQuantityDiscount() {
		return quantityDiscount;
	}

	public void setQuantityDiscount(String quantityDiscount) {
		this.quantityDiscount = quantityDiscount;
	}

	public String getBestseller() {
		return bestseller;
	}

	public void setBestseller(String bestseller) {
		this.bestseller = bestseller;
	}

	public String getAddToCartURL() {
		return addToCartURL;
	}

	public void setAddToCartURL(String addToCartURL) {
		this.addToCartURL = addToCartURL;
	}

	public String getReviewsRSSURL() {
		return reviewsRSSURL;
	}

	public void setReviewsRSSURL(String reviewsRSSURL) {
		this.reviewsRSSURL = reviewsRSSURL;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getOption5() {
		return option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}

	public String getCustomCommissions() {
		return customCommissions;
	}

	public void setCustomCommissions(String customCommissions) {
		this.customCommissions = customCommissions;
	}

	public String getCustomCommissionIsFlatRate() {
		return customCommissionIsFlatRate;
	}

	public void setCustomCommissionIsFlatRate(String customCommissionIsFlatRate) {
		this.customCommissionIsFlatRate = customCommissionIsFlatRate;
	}

	public String getCustomCommissionNewCustomerMultiplier() {
		return customCommissionNewCustomerMultiplier;
	}

	public void setCustomCommissionNewCustomerMultiplier(String customCommissionNewCustomerMultiplier) {
		this.customCommissionNewCustomerMultiplier = customCommissionNewCustomerMultiplier;
	}

	public String getMobileURL() {
		return mobileURL;
	}

	public void setMobileURL(String mobileURL) {
		this.mobileURL = mobileURL;
	}

	public String getMobileImage() {
		return mobileImage;
	}

	public void setMobileImage(String mobileImage) {
		this.mobileImage = mobileImage;
	}

	public String getMobileThumbnail() {
		return mobileThumbnail;
	}

	public void setMobileThumbnail(String mobileThumbnail) {
		this.mobileThumbnail = mobileThumbnail;
	}

	public String getReservedForFutureUse() {
		return reservedForFutureUse;
	}

	public void setReservedForFutureUse(String reservedForFutureUse) {
		this.reservedForFutureUse = reservedForFutureUse;
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

}

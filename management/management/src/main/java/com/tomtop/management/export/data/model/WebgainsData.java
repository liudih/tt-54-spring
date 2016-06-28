package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;

public class WebgainsData implements IData {

	@ExportFileColumn(name = "productName", priority = 1)
	private String productName;// 产品名称

	@ExportFileColumn(name = "brand", priority = 2)
	private String brand;// 品牌

	@ExportFileColumn(name = "shortDescription", priority = 3)
	private String shortDescription;// 简要描述

	@ExportFileColumn(name = "productID", priority = 4)
	private String productID;// SKU

	@ExportFileColumn(name = "description", priority = 5)
	private String description;// 描述

	@ExportFileColumn(name = "deeplink", priority = 6)
	private String deeplink;// 产品URL

	@ExportFileColumn(name = "imageUrl", priority = 7)
	private String imageUrl;// 产品图片URL

	@ExportFileColumn(name = "price", priority = 8)
	private String price;// 产品价格

	@ExportFileColumn(name = "ean", priority = 9)
	private String ean;// EAN

	@ExportFileColumn(name = "upc", priority = 10)
	private String upc;// UPC

	@ExportFileColumn(name = "sku", priority = 11)
	private String sku;// SKU

	@ExportFileColumn(name = "category", priority = 12)
	private String category;

	@ExportFileColumn(name = "inStock", priority = 13)
	private String inStock;// 是否在售，固定填“Y”

	@ExportFileColumn(name = "topSellers", priority = 14)
	private String topSellers;// 是否热卖，固定填“1”

	@ExportFileColumn(name = "currency", priority = 15)
	private String currency;// 货币代码

	@ExportFileColumn(name = "productAvailability", priority = 16)
	private String productAvailability;// 产品可用性（天）

	@ExportFileColumn(name = "sizesAvailable", priority = 17)
	private String sizesAvailable;// 尺寸可供选择（最多50个字符）

	@ExportFileColumn(name = "coloursAvailable", priority = 18)
	private String coloursAvailable;// 颜色可用（最多50个字符）

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeeplink() {
		return deeplink;
	}

	public void setDeeplink(String deeplink) {
		this.deeplink = deeplink;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getInStock() {
		return inStock;
	}

	public void setInStock(String inStock) {
		this.inStock = inStock;
	}

	public String getTopSellers() {
		return topSellers;
	}

	public void setTopSellers(String topSellers) {
		this.topSellers = topSellers;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getProductAvailability() {
		return productAvailability;
	}

	public void setProductAvailability(String productAvailability) {
		this.productAvailability = productAvailability;
	}

	public String getSizesAvailable() {
		return sizesAvailable;
	}

	public void setSizesAvailable(String sizesAvailable) {
		this.sizesAvailable = sizesAvailable;
	}

	public String getColoursAvailable() {
		return coloursAvailable;
	}

	public void setColoursAvailable(String coloursAvailable) {
		this.coloursAvailable = coloursAvailable;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}

package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;
import com.tomtop.management.export.api.IData;

public class TradetrackerData implements IData {
	@ExportFileColumn(name = "sku", priority = 1)
	private String sku;
	@ExportFileColumn(name = "name", priority = 2)
	private String name;
	@ExportFileColumn(name = "currency", priority = 3)
	private String currency;
	@ExportFileColumn(name = "price", priority = 4)
	private String price;
	@ExportFileColumn(name = "description", priority = 5)
	private String description;
	@ExportFileColumn(name = "productURL", priority = 6)
	private String productURL;
	@ExportFileColumn(name = "imageURL", priority = 7)
	private String imageURL;
	@ExportFileColumn(name = "categories", priority = 8)
	private String categories;
	@ExportFileColumn(name = "subcategories", priority = 9)
	private String subcategories;
	@ExportFileColumn(name = "stock", priority = 10)
	private String stock;
	@ExportFileColumn(name = "brand", priority = 11)
	private String brand;
	@ExportFileColumn(name = "deliveryTime", priority = 12)
	private String deliveryTime;

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductURL() {
		return productURL;
	}

	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(String subcategories) {
		this.subcategories = subcategories;
	}

}

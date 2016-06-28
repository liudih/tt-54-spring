package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;

/**
 * 
    * @ClassName: ProductDetailData
    * @Description: admitad产品详细
    * @author liuxin
    * @date 2016年3月14日
    *
 */
@ExportFileColumn(name = "offer", priority = 0)
public class ProductDetailData {
	@ExportFileColumn(name = "url", priority = 1)
	private String url;
	@ExportFileColumn(name = "price", priority = 2)
	private String price;
	@ExportFileColumn(name = "oldPrice", priority = 3)
	private String oldPrice;
	@ExportFileColumn(name = "picture", priority = 4)
	private String pictureUrl;
	@ExportFileColumn(name = "categoryId", priority = 5)
	private Integer categoryId;
	@ExportFileColumn(name = "currencyId", priority = 6)
	private String currencyId;
	@ExportFileColumn(name = "name", priority = 7)
	private String name;
	@ExportFileColumn(name = "vendor", priority = 8)
	private String vendor;// 默认TOMTOP
	@ExportFileColumn(name = "description", priority = 9)
	private String description;
	@ExportFileColumn(name = "typePrefix", priority = 10)
	private String typePrefix;
	@ExportFileColumn(name = "model", priority = 11)
	private String model;
	@ExportFileColumn(name = "vendorCode", priority = 12)
	private String sku;
	@ExportFileColumn(name = "topseller", priority = 13)
	private String topseller;
	@ExportFileColumn(name = "param", priority = 14)
	private String param;
	@ExportFileColumn(name = "store", priority = 15)
	private String store;
	@ExportFileColumn(name = "pickup", priority = 16)
	private String pickup;
	@ExportFileColumn(name = "delivery", priority = 17)
	private String delivery;
	@ExportFileColumn(name = "local_delivery_cost", priority = 18)
	private String local_delivery_cost;
	@ExportFileColumn(name = "sales_notes", priority = 19)
	private String sales_notes;
	@ExportFileColumn(name = "adult", priority = 20)
	private String adult;
	@ExportFileColumn(name = "barcode", priority = 21)
	private String barcode;

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

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTopseller() {
		return topseller;
	}

	public void setTopseller(String topseller) {
		this.topseller = topseller;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getPickup() {
		return pickup;
	}

	public void setPickup(String pickup) {
		this.pickup = pickup;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getLocal_delivery_cost() {
		return local_delivery_cost;
	}

	public void setLocal_delivery_cost(String local_delivery_cost) {
		this.local_delivery_cost = local_delivery_cost;
	}

	public String getSales_notes() {
		return sales_notes;
	}

	public void setSales_notes(String sales_notes) {
		this.sales_notes = sales_notes;
	}

	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}

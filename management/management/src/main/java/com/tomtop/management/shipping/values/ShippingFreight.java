package com.tomtop.management.shipping.values;

public class ShippingFreight {
	private Long countryId;
	private String country;
	private Long storageNameId;
	private String storageName;
	private Long shippingTypeId;
	private String shippingTypeName;
	private String shippingCode;
	private Double weight;
	private Double price;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public Long getShippingTypeId() {
		return shippingTypeId;
	}

	public void setShippingTypeId(Long shippingTypeId) {
		this.shippingTypeId = shippingTypeId;
	}

	public String getShippingTypeName() {
		return shippingTypeName;
	}

	public void setShippingTypeName(String shippingTypeName) {
		this.shippingTypeName = shippingTypeName;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Long getStorageNameId() {
		return storageNameId;
	}

	public void setStorageNameId(Long storageNameId) {
		this.storageNameId = storageNameId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}

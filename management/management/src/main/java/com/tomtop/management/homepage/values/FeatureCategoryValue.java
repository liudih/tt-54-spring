package com.tomtop.management.homepage.values;

import java.sql.Timestamp;
import java.util.List;

public class FeatureCategoryValue {

	private Long id;
	private List<Integer> clients;
	private Integer language_id;
	private Integer number;
	private Integer category_id;
	private String img_url;
	private Integer sort;
	private Integer is_enabled;
	private Integer is_deleted;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;

	private List<FeatureCategoryKeyValue> keys;

	private List<FeatureCategorySkuValue> skus;

	private List<FeatureCategoryBannerValue> banners;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Integer> getClients() {
		return clients;
	}

	public void setClients(List<Integer> clients) {
		this.clients = clients;
	}

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Timestamp getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(Timestamp whenCreated) {
		this.whenCreated = whenCreated;
	}

	public Timestamp getWhenModified() {
		return whenModified;
	}

	public void setWhenModified(Timestamp whenModified) {
		this.whenModified = whenModified;
	}

	public String getWhoCreated() {
		return whoCreated;
	}

	public void setWhoCreated(String whoCreated) {
		this.whoCreated = whoCreated;
	}

	public String getWhoModified() {
		return whoModified;
	}

	public void setWhoModified(String whoModified) {
		this.whoModified = whoModified;
	}

	public List<FeatureCategoryKeyValue> getKeys() {
		return keys;
	}

	public void setKeys(List<FeatureCategoryKeyValue> keys) {
		this.keys = keys;
	}

	public List<FeatureCategorySkuValue> getSkus() {
		return skus;
	}

	public void setSkus(List<FeatureCategorySkuValue> skus) {
		this.skus = skus;
	}

	public List<FeatureCategoryBannerValue> getBanners() {
		return banners;
	}

	public void setBanners(List<FeatureCategoryBannerValue> banners) {
		this.banners = banners;
	}

}

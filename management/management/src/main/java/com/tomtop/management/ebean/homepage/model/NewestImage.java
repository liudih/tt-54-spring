package com.tomtop.management.ebean.homepage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tomtop.management.ebean.manage.model.ManageModel;

@Entity
@Table(name = "home_newest_image")
public class NewestImage extends ManageModel {

	/**
	 * @Fields serialVersionUID : 最新晒图
	 */

	private static final long serialVersionUID = 6490270604401188679L;
	private String sku;
	private String listing_id;
	private Integer client_id;
	private Integer language_id;
	private String img_url;
	private String img_by;
	private String country;
	private Integer is_deleted;
	private Integer is_enabled;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getListing_id() {
		return listing_id;
	}

	public void setListing_id(String listing_id) {
		this.listing_id = listing_id;
	}

	public Integer getClient_id() {
		return client_id;
	}

	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getImg_by() {
		return img_by;
	}

	public void setImg_by(String img_by) {
		this.img_by = img_by;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

package com.tomtop.management.ebean.homepage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tomtop.management.ebean.manage.model.ManageModel;

@Entity
@Table(name = "home_newest_video")
public class NewestVideo extends ManageModel {

	private static final long serialVersionUID = 8895190131924964646L;

	private Integer client_id;
	private Integer language_id;
	private String video_url;
	private String title;
	private String video_by;
	private String country;
	private Integer is_enabled;
	private Integer is_deleted;
	private String listing_id;
	private String sku;

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

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideo_by() {
		return video_by;
	}

	public void setVideo_by(String video_by) {
		this.video_by = video_by;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getListing_id() {
		return listing_id;
	}

	public void setListing_id(String listing_id) {
		this.listing_id = listing_id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

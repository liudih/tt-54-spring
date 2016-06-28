package com.tomtop.management.ebean.homepage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tomtop.management.ebean.manage.model.ManageModel;

@Entity
@Table(name = "home_recent_orders_sku")
public class RecentOsku extends ManageModel {

	/**
	 * @Fields serialVersionUID : 最近买出品sku管理
	 */

	private static final long serialVersionUID = -3479925849785498559L;
	private Integer client_id;
	private Integer language_id;
	private String sku;
	private String listing_id;
	private Integer is_deleted;
	private Integer is_enabled;

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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public String getListing_id() {
		return listing_id;
	}

	public void setListing_id(String listing_id) {
		this.listing_id = listing_id;
	}
}

package com.tomtop.management.ebean.manage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_layout_module_content")
public class ModulContent extends ManageModel {

	private static final long serialVersionUID = 2208190885033366948L;

	private String layout_code;
	private Integer layout_id;
	private String layout_module_code;
	private Integer layout_module_id;
	private Integer client_id;
	private Integer language_id;
	private Integer category_id;
	private Integer is_show;
	private String listing_id;
	private String sku;
	private Integer sort;
	private Integer is_enabled;
	private Integer is_deleted;
	
	public String getLayout_code() {
		return layout_code;
	}

	public void setLayout_code(String layout_code) {
		this.layout_code = layout_code;
	}

	public Integer getLayout_id() {
		return layout_id;
	}

	public void setLayout_id(Integer layout_id) {
		this.layout_id = layout_id;
	}

	public String getLayout_module_code() {
		return layout_module_code;
	}

	public void setLayout_module_code(String layout_module_code) {
		this.layout_module_code = layout_module_code;
	}

	public Integer getLayout_module_id() {
		return layout_module_id;
	}

	public void setLayout_module_id(Integer layout_module_id) {
		this.layout_module_id = layout_module_id;
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

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public Integer getIs_show() {
		return is_show;
	}

	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

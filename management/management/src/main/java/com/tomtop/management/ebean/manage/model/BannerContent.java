package com.tomtop.management.ebean.manage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_banners_content")
public class BannerContent extends ManageModel {

	private static final long serialVersionUID = -5871193192655301158L;
	private String name;
	private String url;
	private String img_url;
	private String title;
	private Integer sort;
	private String layout_code;
	private String banner_code;
	private Integer client_id;
	private Integer language_id;
	private Integer is_enabled;
	private Integer is_deleted;
	private Integer category_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLayout_code() {
		return layout_code;
	}

	public void setLayout_code(String layout_code) {
		this.layout_code = layout_code;
	}

	public String getBanner_code() {
		return banner_code;
	}

	public void setBanner_code(String banner_code) {
		this.banner_code = banner_code;
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

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

}

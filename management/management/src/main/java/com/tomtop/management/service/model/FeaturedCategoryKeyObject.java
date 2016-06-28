package com.tomtop.management.service.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tomtop.management.ebean.homepage.model.FeaturedCategory;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.ManageModel;

public class FeaturedCategoryKeyObject {

	private Long id;
	private Integer client_id;
	private Integer language_id;
	private Integer featured_category_id;
	private String keyword;
	private Integer sort;
	private Integer is_deleted;
	private Integer is_enabled;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private FeaturedCategoryObject featuredCategoryObject;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getFeatured_category_id() {
		return featured_category_id;
	}
	public void setFeatured_category_id(Integer featured_category_id) {
		this.featured_category_id = featured_category_id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public FeaturedCategoryObject getFeaturedCategoryObject() {
		return featuredCategoryObject;
	}
	public void setFeaturedCategoryObject(FeaturedCategoryObject featuredCategoryObject) {
		this.featuredCategoryObject = featuredCategoryObject;
	}

}

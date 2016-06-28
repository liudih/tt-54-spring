package com.tomtop.management.service.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;

import com.tomtop.management.ebean.homepage.model.FeaturedCategory;
import com.tomtop.management.ebean.manage.model.BaseParameter;

public class FeaturedCategoryBannerObject {

	private Long id;
	private Integer client_id;
	private Integer language_id;
	private Integer position_id;
	private String img_url;
	private String title;
	private String url;
	private Integer featured_category_id;
	private Integer sort;
	private Integer is_deleted;
	private Integer is_enabled;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private FeaturedCategoryObject featuredCategoryObject;
	private BaseParameter parameter;

	public BaseParameter getParameter() {
		return parameter;
	}

	public void setParameter(BaseParameter parameter) {
		this.parameter = parameter;
	}

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

	public Integer getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Integer position_id) {
		this.position_id = position_id;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getFeatured_category_id() {
		return featured_category_id;
	}

	public void setFeatured_category_id(Integer featured_category_id) {
		this.featured_category_id = featured_category_id;
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

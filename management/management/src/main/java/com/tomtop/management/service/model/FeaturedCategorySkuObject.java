package com.tomtop.management.service.model;

import java.sql.Timestamp;
import java.util.List;

import com.tomtop.management.ebean.homepage.model.FeaturedCategory;

/**
 * 
 * @ClassName: FeaturedCategorySkuObject
 * @Description: TODO(sku服务管理实体类)
 * @author Guozy
 * @date 2015年12月30日
 *
 */
public class FeaturedCategorySkuObject {

	private Long id;
	private Integer client_id;
	private Integer language_id;
	private String sku;
	private String listing_id;
	private Integer featured_category_id;
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

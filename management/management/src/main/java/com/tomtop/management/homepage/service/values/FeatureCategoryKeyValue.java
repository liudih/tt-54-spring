package com.tomtop.management.homepage.service.values;

public class FeatureCategoryKeyValue {

	private Long id;
	private String key;
	private Integer sort;
	private Integer is_enabled;
	private Integer is_deleted;
	private Integer featured_category_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public Integer getFeatured_category_id() {
		return featured_category_id;
	}

	public void setFeatured_category_id(Integer featured_category_id) {
		this.featured_category_id = featured_category_id;
	}

}

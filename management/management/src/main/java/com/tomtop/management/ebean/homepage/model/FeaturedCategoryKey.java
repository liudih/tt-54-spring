package com.tomtop.management.ebean.homepage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tomtop.management.ebean.manage.model.ManageModel;

/**
 * 
 * @ClassName: FeaturedCategorySku
 * @Description: TODO(首页特别类目搜索Sku管理实体类)
 * @author Guozy
 * @date 2015年12月30日
 *
 */
@Entity
@Table(name = "home_featured_category_key")
public class FeaturedCategoryKey extends ManageModel {

	private static final long serialVersionUID = -1313164964744452388L;
	private Integer client_id;
	private Integer language_id;
	private Integer featured_category_id;
	private String keyword;
	private Integer sort;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

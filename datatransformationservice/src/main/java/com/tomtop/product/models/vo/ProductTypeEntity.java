package com.tomtop.product.models.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品类型
 * 
 * @author ztiny
 * @Date 2015-12-19
 */
public class ProductTypeEntity {

	// 国际化语言ID
	private Integer languageId;
	// 产品类目ID
	private Integer productTypeId;
	// 产品名称
	private String productTypeName;
	private String cpath;
	/**
	 * 级别
	 */
	private Integer level;
	// 类目子类型
	private List<ProductTypeEntity> children = new ArrayList<ProductTypeEntity>();
	private Integer sort;

	public ProductTypeEntity() {

	}

	public ProductTypeEntity(Integer languageId, Integer productTypeId,
			String productTypeName, Integer level,
			List<ProductTypeEntity> childs, String cpath, Integer sort) {
		this.languageId = languageId;
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
		this.children = childs;
		this.level = level;
		this.cpath = cpath;
		this.sort = sort;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public List<ProductTypeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<ProductTypeEntity> childs) {
		this.children = childs;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCpath() {
		return cpath;
	}

	public void setCpath(String cpath) {
		this.cpath = cpath;
	}

}

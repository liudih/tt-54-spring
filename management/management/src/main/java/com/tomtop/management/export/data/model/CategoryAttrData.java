package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;

@ExportFileColumn(name = "category", priority = 0, item = "t")
public class CategoryAttrData {

	@ExportFileColumn(name = "category", priority = 1)
	private String category;

	@ExportFileColumn(name = "id", priority = 2, type = "attr")
	private Integer id;

	@ExportFileColumn(name = "parent_id", priority = 3, type = "attr")
	private Integer parent_id;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

}

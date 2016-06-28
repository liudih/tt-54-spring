package com.tomtop.management.export.data.model;

import java.util.List;

import com.tomtop.management.export.annotation.ExportFileColumn;

@ExportFileColumn(name = "categories", priority = 0)
public class CategoryData {
	@ExportFileColumn(name = "category", priority = 1)
	private List<CategoryAttrData> category;

	public List<CategoryAttrData> getCategory() {
		return category;
	}

	public void setCategory(List<CategoryAttrData> category) {
		this.category = category;
	}

}

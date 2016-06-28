package com.tomtop.product.models.vo;

/**
 * 多属性
 * 
 * @author ztiny
 * @Date 2015-12-21
 */
public class AttributeItem {
	// 属性对应的ID
	private Integer keyId;
	// 属性对应的键
	private String key;
	// 属性值对应的ID
	private Integer valueId;
	// 属性值对应的真实值
	private String value;
	// 语言ID
	private Integer languageId;
	// 是否展示小图标
	private Boolean showImg;
	// 属性是否可见
	private Boolean visible;

	public AttributeItem() {

	}

	public AttributeItem(Integer keyId, String key, Integer valueId,
			String value, Integer languangeId, Boolean showImg, Boolean visible) {
		this.keyId = keyId;
		this.key = key;
		this.valueId = valueId;
		this.value = value;
		this.languageId = languangeId;
		this.showImg = showImg;
		this.visible = visible;
	}

	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

	public Integer getValueId() {
		return valueId;
	}

	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languangeId) {
		this.languageId = languangeId;
	}

	public Boolean getShowImg() {
		return showImg;
	}

	public void setShowImg(Boolean showImg) {
		this.showImg = showImg;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}

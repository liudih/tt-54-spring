package com.tomtop.product.models.vo;

/**
 * 标签实体对象
 * 
 * @author ztiny
 *
 */
public class TagEntity {

	/** 主键ID */
	private Integer id;
	/** 标签名称 */
	private String tagName;
	/** 发布时间 */
	private String releaseTime;

	public TagEntity(Integer id, String tagName, String releaseTime) {
		this.id = id;
		this.tagName = tagName;
		this.releaseTime = releaseTime;
	}

	public TagEntity() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

}

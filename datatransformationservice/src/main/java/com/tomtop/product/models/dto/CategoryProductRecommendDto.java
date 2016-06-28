package com.tomtop.product.models.dto;

import java.util.Date;

public class CategoryProductRecommendDto {

	private int iid;
	private int categoryid;
	private String categoryname;
	private String categoryfullname;
	private int parentid;
	private String parentname;
	private int level;
	private String sku;
	private int status;
	private Date createdate;
	private int sequence;
	private String createby;
	private int iwebsiteid;
	private String cdevice;

	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getCategoryfullname() {
		return categoryfullname;
	}

	public void setCategoryfullname(String categoryfullname) {
		this.categoryfullname = categoryfullname;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public int getIwebsiteid() {
		return iwebsiteid;
	}

	public void setIwebsiteid(int iwebsiteid) {
		this.iwebsiteid = iwebsiteid;
	}

	public String getCdevice() {
		return cdevice;
	}

	public void setCdevice(String cdevice) {
		this.cdevice = cdevice;
	}
}

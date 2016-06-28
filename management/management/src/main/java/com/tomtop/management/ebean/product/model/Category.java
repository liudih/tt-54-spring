package com.tomtop.management.ebean.product.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_category_website")
public class Category extends ProductModel {

	@Id
	private Integer iid;
	private Integer icategoryid;
	private Integer iwebsiteid;
	private Integer iparentid;
	private String cpath;
	private Integer ilevel;
	private Integer iposition;
	private Integer ichildrencount;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getIparentid() {
		return iparentid;
	}

	public void setIparentid(Integer iparentid) {
		this.iparentid = iparentid;
	}

	public String getCpath() {
		return cpath;
	}

	public void setCpath(String cpath) {
		this.cpath = cpath;
	}

	public Integer getIlevel() {
		return ilevel;
	}

	public void setIlevel(Integer ilevel) {
		this.ilevel = ilevel;
	}

	public Integer getIposition() {
		return iposition;
	}

	public void setIposition(Integer iposition) {
		this.iposition = iposition;
	}

	public Integer getIchildrencount() {
		return ichildrencount;
	}

	public void setIchildrencount(Integer ichildrencount) {
		this.ichildrencount = ichildrencount;
	}

	public Integer getIcategoryid() {
		return icategoryid;
	}

	public void setIcategoryid(Integer icategoryid) {
		this.icategoryid = icategoryid;
	}

	public Integer getIwebsiteid() {
		return iwebsiteid;
	}

	public void setIwebsiteid(Integer iwebsiteid) {
		this.iwebsiteid = iwebsiteid;
	}

}

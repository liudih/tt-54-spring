package com.tomtop.management.ebean.product.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_product_base")
public class Product extends ProductModel {

	@Id
	private Long iid;

	private String clistingid;

	private Integer iwebsiteid;

	private String csku;

	private Integer istatus;

	private Date dnewformdate;

	private Date dnewtodate;

	private Boolean bspecial;

	private String cvideoaddress;

	private Integer iqty;

	private Double fprice;

	private Double fcostprice;

	private Double fweight;

	private Boolean bmultiattribute;

	private String cparentsku;

	private Boolean bvisible;

	private Boolean bpulish;

	private String ccreateuser;

	private Date dcreatedate;

	private Double ffreight;

	private Boolean bmain;

	private Boolean bactivity;

	public Long getIid() {
		return iid;
	}

	public void setIid(Long iid) {
		this.iid = iid;
	}

	public String getClistingid() {
		return clistingid;
	}

	public void setClistingid(String clistingid) {
		this.clistingid = clistingid;
	}

	public Integer getIwebsiteid() {
		return iwebsiteid;
	}

	public void setIwebsiteid(Integer iwebsiteid) {
		this.iwebsiteid = iwebsiteid;
	}

	public String getCsku() {
		return csku;
	}

	public void setCsku(String csku) {
		this.csku = csku;
	}

	public Integer getIstatus() {
		return istatus;
	}

	public void setIstatus(Integer istatus) {
		this.istatus = istatus;
	}

	public Date getDnewformdate() {
		return dnewformdate;
	}

	public void setDnewformdate(Date dnewformdate) {
		this.dnewformdate = dnewformdate;
	}

	public Date getDnewtodate() {
		return dnewtodate;
	}

	public void setDnewtodate(Date dnewtodate) {
		this.dnewtodate = dnewtodate;
	}

	public Boolean getBspecial() {
		return bspecial;
	}

	public void setBspecial(Boolean bspecial) {
		this.bspecial = bspecial;
	}

	public String getCvideoaddress() {
		return cvideoaddress;
	}

	public void setCvideoaddress(String cvideoaddress) {
		this.cvideoaddress = cvideoaddress;
	}

	public Integer getIqty() {
		return iqty;
	}

	public void setIqty(Integer iqty) {
		this.iqty = iqty;
	}

	public Double getFprice() {
		return fprice;
	}

	public void setFprice(Double fprice) {
		this.fprice = fprice;
	}

	public Double getFcostprice() {
		return fcostprice;
	}

	public void setFcostprice(Double fcostprice) {
		this.fcostprice = fcostprice;
	}

	public Double getFweight() {
		return fweight;
	}

	public void setFweight(Double fweight) {
		this.fweight = fweight;
	}

	public Boolean getBmultiattribute() {
		return bmultiattribute;
	}

	public void setBmultiattribute(Boolean bmultiattribute) {
		this.bmultiattribute = bmultiattribute;
	}

	public String getCparentsku() {
		return cparentsku;
	}

	public void setCparentsku(String cparentsku) {
		this.cparentsku = cparentsku;
	}

	public Boolean getBvisible() {
		return bvisible;
	}

	public void setBvisible(Boolean bvisible) {
		this.bvisible = bvisible;
	}

	public Boolean getBpulish() {
		return bpulish;
	}

	public void setBpulish(Boolean bpulish) {
		this.bpulish = bpulish;
	}

	public String getCcreateuser() {
		return ccreateuser;
	}

	public void setCcreateuser(String ccreateuser) {
		this.ccreateuser = ccreateuser;
	}

	public Date getDcreatedate() {
		return dcreatedate;
	}

	public void setDcreatedate(Date dcreatedate) {
		this.dcreatedate = dcreatedate;
	}

	public Double getFfreight() {
		return ffreight;
	}

	public void setFfreight(Double ffreight) {
		this.ffreight = ffreight;
	}

	public Boolean getBmain() {
		return bmain;
	}

	public void setBmain(Boolean bmain) {
		this.bmain = bmain;
	}

	public Boolean getBactivity() {
		return bactivity;
	}

	public void setBactivity(Boolean bactivity) {
		this.bactivity = bactivity;
	}

}

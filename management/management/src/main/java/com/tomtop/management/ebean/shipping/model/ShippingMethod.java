package com.tomtop.management.ebean.shipping.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenCreated;

@Entity
@Table(name = "shipping_method")
public class ShippingMethod extends Model {
	
	public static EbeanServer db() {
		return Ebean.getServer("manage");
	}

	@Id
	private Long iid;
	private Long istorageid;
	private Integer benabled;
	private Integer bexistfree;
	private Double ffreebeginprice;
	private Double ffreeendprice;
	private String ccountrys;
	private String crule;
	private String csuperrule;
	@WhenCreated
	private Timestamp dcreatedate;
	private Double fbeginprice;
	private Double fendprice;
	private Integer bistracking;
	private Integer bisspecial;
	private String ccode;
	private Integer istartweight;
	private Integer iendweight;

	public Long getIid() {
		return iid;
	}

	public void setIid(Long iid) {
		this.iid = iid;
	}

	public Long getIstorageid() {
		return istorageid;
	}

	public void setIstorageid(Long istorageid) {
		this.istorageid = istorageid;
	}

	public Integer getBenabled() {
		return benabled;
	}

	public void setBenabled(Integer benabled) {
		this.benabled = benabled;
	}

	public Integer getBexistfree() {
		return bexistfree;
	}

	public void setBexistfree(Integer bexistfree) {
		this.bexistfree = bexistfree;
	}

	public Double getFfreebeginprice() {
		return ffreebeginprice;
	}

	public void setFfreebeginprice(Double ffreebeginprice) {
		this.ffreebeginprice = ffreebeginprice;
	}

	public Double getFfreeendprice() {
		return ffreeendprice;
	}

	public void setFfreeendprice(Double ffreeendprice) {
		this.ffreeendprice = ffreeendprice;
	}

	public String getCcountrys() {
		return ccountrys;
	}

	public void setCcountrys(String ccountrys) {
		this.ccountrys = ccountrys;
	}

	public String getCrule() {
		return crule;
	}

	public void setCrule(String crule) {
		this.crule = crule;
	}

	public String getCsuperrule() {
		return csuperrule;
	}

	public void setCsuperrule(String csuperrule) {
		this.csuperrule = csuperrule;
	}

	public Timestamp getDcreatedate() {
		return dcreatedate;
	}

	public void setDcreatedate(Timestamp dcreatedate) {
		this.dcreatedate = dcreatedate;
	}

	public Double getFbeginprice() {
		return fbeginprice;
	}

	public void setFbeginprice(Double fbeginprice) {
		this.fbeginprice = fbeginprice;
	}

	public Double getFendprice() {
		return fendprice;
	}

	public void setFendprice(Double fendprice) {
		this.fendprice = fendprice;
	}

	public Integer getBistracking() {
		return bistracking;
	}

	public void setBistracking(Integer bistracking) {
		this.bistracking = bistracking;
	}

	public Integer getBisspecial() {
		return bisspecial;
	}

	public void setBisspecial(Integer bisspecial) {
		this.bisspecial = bisspecial;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public Integer getIstartweight() {
		return istartweight;
	}

	public void setIstartweight(Integer istartweight) {
		this.istartweight = istartweight;
	}

	public Integer getIendweight() {
		return iendweight;
	}

	public void setIendweight(Integer iendweight) {
		this.iendweight = iendweight;
	}

}

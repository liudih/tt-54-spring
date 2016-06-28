package com.tomtop.management.ebean.base.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhoCreated;

@Entity
@Table(name = "t_currency")
public class BaseCurrency extends BaseModel {
	@Id
	private Long iid;
	private String ccode;
	private String csymbol;
	private Double fexchangerate;
	@WhoCreated
	private String ccreateuser;
	@WhenCreated
	private Timestamp dcreatedate;
	private String cversionsid;
	private Boolean bshow;

	public Long getIid() {
		return iid;
	}

	public void setIid(Long iid) {
		this.iid = iid;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getCsymbol() {
		return csymbol;
	}

	public void setCsymbol(String csymbol) {
		this.csymbol = csymbol;
	}

	public Double getFexchangerate() {
		return fexchangerate;
	}

	public void setFexchangerate(Double fexchangerate) {
		this.fexchangerate = fexchangerate;
	}

	public String getCcreateuser() {
		return ccreateuser;
	}

	public void setCcreateuser(String ccreateuser) {
		this.ccreateuser = ccreateuser;
	}

	public Timestamp getDcreatedate() {
		return dcreatedate;
	}

	public void setDcreatedate(Timestamp dcreatedate) {
		this.dcreatedate = dcreatedate;
	}

	public String getCversionsid() {
		return cversionsid;
	}

	public void setCversionsid(String cversionsid) {
		this.cversionsid = cversionsid;
	}

	public Boolean getBshow() {
		return bshow;
	}

	public void setBshow(Boolean bshow) {
		this.bshow = bshow;
	}

}

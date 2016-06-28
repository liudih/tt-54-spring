package com.tomtop.management.ebean.base.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhoCreated;

@Entity
@Table(name = "t_currency_rate")
public class BaseCurrencyRate extends BaseModel {
	@Id
	private Long iid;
	private String ccode;
	private Double fexchangerate;
	@WhoCreated
	private String ccreateuser;
	private Boolean buse;
	@WhenCreated
	private Timestamp dcreatedate;

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

	public Boolean getBuse() {
		return buse;
	}

	public void setBuse(Boolean buse) {
		this.buse = buse;
	}

	public Timestamp getDcreatedate() {
		return dcreatedate;
	}

	public void setDcreatedate(Timestamp dcreatedate) {
		this.dcreatedate = dcreatedate;
	}

}

package com.tomtop.management.ebean.base.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_storage")
public class Storage extends BaseModel {
	@Id
	private Long iid;
	private Integer ioverseas;
	private String cstoragename;
	private String ccreateuser;
	private Timestamp dcreatedate;
	private Long iparentstorage;

	public Long getIid() {
		return iid;
	}

	public void setIid(Long iid) {
		this.iid = iid;
	}

	public Integer getIoverseas() {
		return ioverseas;
	}

	public void setIoverseas(Integer ioverseas) {
		this.ioverseas = ioverseas;
	}

	public String getCstoragename() {
		return cstoragename;
	}

	public void setCstoragename(String cstoragename) {
		this.cstoragename = cstoragename;
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

	public Long getIparentstorage() {
		return iparentstorage;
	}

	public void setIparentstorage(Long iparentstorage) {
		this.iparentstorage = iparentstorage;
	}

}

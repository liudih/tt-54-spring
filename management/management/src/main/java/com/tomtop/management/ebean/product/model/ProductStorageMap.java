package com.tomtop.management.ebean.product.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_product_storage_map")
public class ProductStorageMap extends ProductModel {

	@Id
	private Long iid;
	private String clistingid;
	private String csku;
	private Long istorageid;
	private String ccreateuser;
	private Timestamp dcreatedate;
	private Boolean bavailable;
	private Long ilogisticstemplateid;

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

	public String getCsku() {
		return csku;
	}

	public void setCsku(String csku) {
		this.csku = csku;
	}

	public Long getIstorageid() {
		return istorageid;
	}

	public void setIstorageid(Long istorageid) {
		this.istorageid = istorageid;
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

	public Boolean getBavailable() {
		return bavailable;
	}

	public void setBavailable(Boolean bavailable) {
		this.bavailable = bavailable;
	}

	public Long getIlogisticstemplateid() {
		return ilogisticstemplateid;
	}

	public void setIlogisticstemplateid(Long ilogisticstemplateid) {
		this.ilogisticstemplateid = ilogisticstemplateid;
	}

}

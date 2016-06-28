package com.tomtop.management.ebean.product.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_product_saleprice")
public class ProductSalePrice extends ProductModel {
	
	@Id
	private Long iid;

	private String clistingid;

	private String csku;

	private Float fsaleprice;

	private Timestamp dbegindate;

	private Timestamp denddate;

	private Timestamp dcreatedate;

	private String ccreateuser;

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

	public Float getFsaleprice() {
		return fsaleprice;
	}

	public void setFsaleprice(Float fsaleprice) {
		this.fsaleprice = fsaleprice;
	}

	public Timestamp getDbegindate() {
		return dbegindate;
	}

	public void setDbegindate(Timestamp dbegindate) {
		this.dbegindate = dbegindate;
	}

	public Timestamp getDcreatedate() {
		return dcreatedate;
	}

	public void setDcreatedate(Timestamp dcreatedate) {
		this.dcreatedate = dcreatedate;
	}

	public Timestamp getDenddate() {
		return denddate;
	}

	public void setDenddate(Timestamp denddate) {
		this.denddate = denddate;
	}

	public String getCcreateuser() {
		return ccreateuser;
	}

	public void setCcreateuser(String ccreateuser) {
		this.ccreateuser = ccreateuser;
	}

}

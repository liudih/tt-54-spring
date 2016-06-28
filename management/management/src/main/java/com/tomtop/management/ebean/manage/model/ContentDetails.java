package com.tomtop.management.ebean.manage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "content_details")
public class ContentDetails extends ManageModel {

	private static final long serialVersionUID = 5078571570253810537L;
	private Long catalogue_id;
	private Integer is_deleted;

	public Long getCatalogue_id() {
		return catalogue_id;
	}

	public void setCatalogue_id(Long catalogue_id) {
		this.catalogue_id = catalogue_id;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

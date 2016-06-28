package com.tomtop.management.ebean.manage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_resource")
public class Resource extends ManageModel {

	private static final long serialVersionUID = 3097291812119295765L;
	
	@Column(name = "language_id")
	private Integer language_id;
	@Column(name = "client_id")
	private Integer client_id;
	@Column(name = "key")
	private String key;
	@Column(name = "value")
	private String value;
	@Column(name = "is_deleted")
	private Integer is_deleted;
	@Column(name = "is_enabled")
	private Integer is_enabled;

	public Integer getClient_id() {
		return client_id;
	}

	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

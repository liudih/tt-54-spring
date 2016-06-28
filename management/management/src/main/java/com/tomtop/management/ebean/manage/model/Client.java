package com.tomtop.management.ebean.manage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_client")
public class Client extends ManageModel {

	private static final long serialVersionUID = 3149826538259605534L;
	private String name;
	private Integer platform;
	private String remark;
	private Integer site_id;
	private Integer is_deleted;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Integer is_enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSite_id() {
		return site_id;
	}

	public void setSite_id(Integer site_id) {
		this.site_id = site_id;
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

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

}

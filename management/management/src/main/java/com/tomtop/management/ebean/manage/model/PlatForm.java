package com.tomtop.management.ebean.manage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_platform")
public class PlatForm extends ManageModel {

	/**
	 * @Fields serialVersionUID : 平台实体
	 */

	private static final long serialVersionUID = 1L;

	private Integer language_id;
	private String platform;
	private String remark;
	private Integer is_deleted;
	private Integer is_enabled;

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

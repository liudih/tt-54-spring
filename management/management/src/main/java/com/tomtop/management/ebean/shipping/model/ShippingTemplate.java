package com.tomtop.management.ebean.shipping.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tomtop.management.ebean.manage.model.ManageModel;

@Entity
@Table(name = "shipping_template")
public class ShippingTemplate extends ManageModel {

	/**
	 * @Fields serialVersionUID : 参数实体
	 */

	private static final long serialVersionUID = 1002864950273401195L;
	private String template_name;
	private Integer is_deleted;
	private Integer is_enabled;

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
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

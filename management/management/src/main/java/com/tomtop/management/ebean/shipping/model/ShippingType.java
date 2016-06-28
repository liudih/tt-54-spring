package com.tomtop.management.ebean.shipping.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tomtop.management.ebean.manage.model.ManageModel;

@Entity
@Table(name = "shipping_type")
public class ShippingType extends ManageModel {

	/**
	 * @Fields serialVersionUID : 参数实体
	 */

	private static final long serialVersionUID = 1002864950273401195L;
	private String type_name;
	private String shipping_code;
	private Integer is_deleted;
	private Integer is_enabled;
	private Integer shipping_sequence;

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getShipping_code() {
		return shipping_code;
	}

	public void setShipping_code(String shipping_code) {
		this.shipping_code = shipping_code;
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

	public Integer getShipping_sequence() {
		return shipping_sequence;
	}

	public void setShipping_sequence(Integer shipping_sequence) {
		this.shipping_sequence = shipping_sequence;
	}

}

package com.tomtop.management.ebean.shipping.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Model;

@Entity
@Table(name = "shipping_type_displayname_description")
public class ShippingDescription extends Model {

	public static EbeanServer db() {
		return Ebean.getServer("manage");
	}

	/**
	 * 物流类型管理多语言
	 */

	private Integer shipping_type_id;

	@Id
	private Integer id;

	private Integer language_id;

	private String display_name;

	private String description;

	public Integer getShipping_type_id() {
		return shipping_type_id;
	}

	public void setShipping_type_id(Integer shipping_type_id) {
		this.shipping_type_id = shipping_type_id;
	}

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

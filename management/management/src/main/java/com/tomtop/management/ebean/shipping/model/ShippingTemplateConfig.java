package com.tomtop.management.ebean.shipping.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tomtop.management.ebean.manage.model.ManageModel;

@Entity
@Table(name = "shipping_template_config")
public class ShippingTemplateConfig extends ManageModel {

	private static final long serialVersionUID = -3287421389458493375L;
	private Long shipping_template_id;
	private Long shipping_type_id;
	private Long warehouse_id;
	private String country;
	private Long filter_id;
	private String priority_shipping_code;
	private Integer is_freeshipping;
	private Double start_amount;
	private Double amount_limit;
	private Double start_weight;
	private Double weight_limit;
	private Integer is_especial;
	private Double extra_charge;
	private String extra_charge_note;
	private Integer is_enabled;
	private Integer is_deleted;
	private Integer country_add_type;
	private Integer is_calculate_weight;

	public Long getShipping_template_id() {
		return shipping_template_id;
	}

	public void setShipping_template_id(Long shipping_template_id) {
		this.shipping_template_id = shipping_template_id;
	}

	public Long getShipping_type_id() {
		return shipping_type_id;
	}

	public void setShipping_type_id(Long shipping_type_id) {
		this.shipping_type_id = shipping_type_id;
	}

	public Long getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(Long warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getFilter_id() {
		return filter_id;
	}

	public void setFilter_id(Long filter_id) {
		this.filter_id = filter_id;
	}

	public String getPriority_shipping_code() {
		return priority_shipping_code;
	}

	public void setPriority_shipping_code(String priority_shipping_code) {
		this.priority_shipping_code = priority_shipping_code;
	}

	public Integer getIs_freeshipping() {
		return is_freeshipping;
	}

	public void setIs_freeshipping(Integer is_freeshipping) {
		this.is_freeshipping = is_freeshipping;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Double getAmount_limit() {
		return amount_limit;
	}

	public void setAmount_limit(Double amount_limit) {
		this.amount_limit = amount_limit;
	}

	public Double getWeight_limit() {
		return weight_limit;
	}

	public void setWeight_limit(Double weight_limit) {
		this.weight_limit = weight_limit;
	}

	public Integer getIs_especial() {
		return is_especial;
	}

	public void setIs_especial(Integer is_especial) {
		this.is_especial = is_especial;
	}

	public Double getExtra_charge() {
		return extra_charge;
	}

	public void setExtra_charge(Double extra_charge) {
		this.extra_charge = extra_charge;
	}

	public String getExtra_charge_note() {
		return extra_charge_note;
	}

	public void setExtra_charge_note(String extra_charge_note) {
		this.extra_charge_note = extra_charge_note;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getCountry_add_type() {
		return country_add_type;
	}

	public void setCountry_add_type(Integer country_add_type) {
		this.country_add_type = country_add_type;
	}

	public Integer getIs_calculate_weight() {
		return is_calculate_weight;
	}

	public void setIs_calculate_weight(Integer is_calculate_weight) {
		this.is_calculate_weight = is_calculate_weight;
	}
	
	public Double getStart_amount() {
		return start_amount;
	}

	public void setStart_amount(Double start_amount) {
		this.start_amount = start_amount;
	}

	public Double getStart_weight() {
		return start_weight;
	}

	public void setStart_weight(Double start_weight) {
		this.start_weight = start_weight;
	}

}

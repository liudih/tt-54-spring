package com.tomtop.management.shipping.values;

import java.sql.Timestamp;

import com.tomtop.management.ebean.base.model.Storage;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.shipping.model.ShippingTemplate;
import com.tomtop.management.ebean.shipping.model.ShippingType;


public class ShippingTemplateConfigValue {
	private Long id;
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
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;
	private Integer is_calculate_weight;

	private ShippingTemplate shippingTemplate;
	private ShippingType shippingType;
	private BaseParameter filter;
	private Storage storage;
	private String crateTime;
	private String updateTime;
	private String isEnabled;
	private String amountLimit;
	private String weightLimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Timestamp getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(Timestamp whenCreated) {
		this.whenCreated = whenCreated;
	}

	public Timestamp getWhenModified() {
		return whenModified;
	}

	public void setWhenModified(Timestamp whenModified) {
		this.whenModified = whenModified;
	}

	public String getWhoCreated() {
		return whoCreated;
	}

	public void setWhoCreated(String whoCreated) {
		this.whoCreated = whoCreated;
	}

	public String getWhoModified() {
		return whoModified;
	}

	public void setWhoModified(String whoModified) {
		this.whoModified = whoModified;
	}

	public String getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(String crateTime) {
		this.crateTime = crateTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public BaseParameter getFilter() {
		return filter;
	}

	public void setFilter(BaseParameter filter) {
		this.filter = filter;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public Integer getCountry_add_type() {
		return country_add_type;
	}

	public void setCountry_add_type(Integer country_add_type) {
		this.country_add_type = country_add_type;
	}

	public ShippingTemplate getShippingTemplate() {
		return shippingTemplate;
	}

	public void setShippingTemplate(ShippingTemplate shippingTemplate) {
		this.shippingTemplate = shippingTemplate;
	}

	public ShippingType getShippingType() {
		return shippingType;
	}

	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
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

	public String getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(String amountLimit) {
		this.amountLimit = amountLimit;
	}

	public String getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(String weightLimit) {
		this.weightLimit = weightLimit;
	}

}

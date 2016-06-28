package com.tomtop.management.shipping.values;

import java.sql.Timestamp;
import java.util.List;

public class ShippingTypeValue {
	private Long id;
	private String type_name;
	private Integer is_deleted;
	private Integer is_enabled;
	private Integer shipping_sequence;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;
	private String createTime;
	private String updateTime;
	private List<ShippingDesValue> sdList;
	private String shipping_code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<ShippingDesValue> getSdList() {
		return sdList;
	}

	public void setSdList(List<ShippingDesValue> sdList) {
		this.sdList = sdList;
	}

	public String getShipping_code() {
		return shipping_code;
	}

	public void setShipping_code(String shipping_code) {
		this.shipping_code = shipping_code;
	}

	public Integer getShipping_sequence() {
		return shipping_sequence;
	}

	public void setShipping_sequence(Integer shipping_sequence) {
		this.shipping_sequence = shipping_sequence;
	}

}

package com.tomtop.management.ebean.manage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_layout_module")
public class LoyoutModul extends ManageModel {

	private static final long serialVersionUID = -8355112887729386465L;
	private String code;// 模块标识
	private String name;// 模块名称
	private String layout_code;// 布局标识
	private Integer layout_id;// 布局id
	private Integer client_id;// 客户端id
	private Integer language_id;// 语言id
	private Integer position_id;// 位置
	private String url;// 路由
	private Integer number;// 模块显示的数量
	private Integer sort;// 排序号
	private Integer is_enabled;//状态
	private Integer is_deleted;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLayout_code() {
		return layout_code;
	}

	public void setLayout_code(String layout_code) {
		this.layout_code = layout_code;
	}

	public Integer getLayout_id() {
		return layout_id;
	}

	public void setLayout_id(Integer layout_id) {
		this.layout_id = layout_id;
	}

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

	public Integer getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Integer position_id) {
		this.position_id = position_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

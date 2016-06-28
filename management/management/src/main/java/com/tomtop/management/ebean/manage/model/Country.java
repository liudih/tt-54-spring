package com.tomtop.management.ebean.manage.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_country")
public class Country extends ManageModel{

	private static final long serialVersionUID = -5302484783925115544L;
	private String name;
	private String national_flag_img_url;
	private String iso_code_two;
	private String iso_code_three;
	private String address_format;
	private Integer is_required_postcode;
	private String currency;
	private Integer official_language_id;
	private Integer language_id;
	private String weigth_unit;
	private String length_unit;
	private Integer sort;
	private Integer is_deleted;
	private Integer is_enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNational_flag_img_url() {
		return national_flag_img_url;
	}

	public void setNational_flag_img_url(String national_flag_img_url) {
		this.national_flag_img_url = national_flag_img_url;
	}

	public String getIso_code_two() {
		return iso_code_two;
	}

	public void setIso_code_two(String iso_code_two) {
		this.iso_code_two = iso_code_two;
	}

	public String getIso_code_three() {
		return iso_code_three;
	}

	public void setIso_code_three(String iso_code_three) {
		this.iso_code_three = iso_code_three;
	}

	public String getAddress_format() {
		return address_format;
	}

	public void setAddress_format(String address_format) {
		this.address_format = address_format;
	}

	public Integer getIs_required_postcode() {
		return is_required_postcode;
	}

	public void setIs_required_postcode(Integer is_required_postcode) {
		this.is_required_postcode = is_required_postcode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getOfficial_language_id() {
		return official_language_id;
	}

	public void setOfficial_language_id(Integer official_language_id) {
		this.official_language_id = official_language_id;
	}

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}

	public String getWeigth_unit() {
		return weigth_unit;
	}

	public void setWeigth_unit(String weigth_unit) {
		this.weigth_unit = weigth_unit;
	}

	public String getLength_unit() {
		return length_unit;
	}

	public void setLength_unit(String length_unit) {
		this.length_unit = length_unit;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

package com.tomtop.management.service.model;

import java.util.ArrayList;

import com.tomtop.management.ebean.manage.model.BannerContent;

public class BannerRequest {
	private Long id;
	private String name;
	private String code;
	private Integer client_id;
	private Integer language_id;
	private String layout_code;
	private Integer position_id;
	private Integer is_enabled;
	private String clients;
	private String languages;
	private ArrayList<BannerContent> bcList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getLayout_code() {
		return layout_code;
	}

	public void setLayout_code(String layout_code) {
		this.layout_code = layout_code;
	}

	public Integer getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Integer position_id) {
		this.position_id = position_id;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public String getClients() {
		return clients;
	}

	public void setClients(String clients) {
		this.clients = clients;
	}

	public ArrayList<BannerContent> getBcList() {
		return bcList;
	}

	public void setBcList(ArrayList<BannerContent> bcList) {
		this.bcList = bcList;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

}

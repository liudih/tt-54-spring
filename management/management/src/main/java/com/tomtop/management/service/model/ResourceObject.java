package com.tomtop.management.service.model;

import java.sql.Timestamp;

import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Category;

public class ResourceObject {

	private Integer id;
	private Integer client_id;
	private Integer language_id;
	private String key;
	private String value;
	private Integer is_deleted;
	private Integer is_enabled;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;

	private Client client;
	private Language language;
	private String crateTime;
	private String updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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

}

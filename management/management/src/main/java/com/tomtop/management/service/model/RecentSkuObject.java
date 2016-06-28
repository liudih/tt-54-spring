package com.tomtop.management.service.model;

import java.sql.Timestamp;

import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;

/**
 * 
 * @ClassName: RecentSkuObject
 * @Description: 最近卖出品SKu业务实体
 * @author liuxin
 * @date 2015年12月28日
 *
 */
public class RecentSkuObject {

	private Integer client_id;
	private Integer language_id;
	private String sku;
	private Integer is_deleted;
	private Integer is_enabled;

	private Long id;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;
	private String createTime;
	private String updateTime;
	private Client client;
	private Language language;

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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}

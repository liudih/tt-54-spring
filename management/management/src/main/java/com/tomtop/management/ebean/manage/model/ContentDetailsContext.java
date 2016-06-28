package com.tomtop.management.ebean.manage.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Model;

@Entity
@Table(name = "content_details_context")
public class ContentDetailsContext extends Model {

	public static EbeanServer db() {
		return Ebean.getServer("manage");
	}

	@Id
	private Long id;
	private Long details_id;
	private Long language_id;
	private String title;
	private String url;
	private String content;
	private String meta_title;
	private String meta_keyword;
	private String meta_description;
	private Integer is_enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDetails_id() {
		return details_id;
	}

	public void setDetails_id(Long details_id) {
		this.details_id = details_id;
	}

	public Long getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Long language_id) {
		this.language_id = language_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMeta_title() {
		return meta_title;
	}

	public void setMeta_title(String meta_title) {
		this.meta_title = meta_title;
	}

	public String getMeta_keyword() {
		return meta_keyword;
	}

	public void setMeta_keyword(String meta_keyword) {
		this.meta_keyword = meta_keyword;
	}

	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

}

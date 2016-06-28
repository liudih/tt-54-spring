package com.tomtop.management.ebean.search.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_keyword_suggest")
public class KeywordSuggest extends SearchModel {

	@Id
	private Long iid;

	private String ckeyword;

	private int ilanguageid;

	private int iwebsiteid;

	public Long getIid() {
		return iid;
	}

	public void setIid(Long iid) {
		this.iid = iid;
	}

	public String getCkeyword() {
		return ckeyword;
	}

	public void setCkeyword(String ckeyword) {
		this.ckeyword = ckeyword;
	}

	public int getIlanguageid() {
		return ilanguageid;
	}

	public void setIlanguageid(int ilanguageid) {
		this.ilanguageid = ilanguageid;
	}

	public int getIwebsiteid() {
		return iwebsiteid;
	}

	public void setIwebsiteid(int iwebsiteid) {
		this.iwebsiteid = iwebsiteid;
	}

}

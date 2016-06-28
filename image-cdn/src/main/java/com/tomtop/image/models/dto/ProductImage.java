package com.tomtop.image.models.dto;

public class ProductImage {
	private Integer iid;
	private String clistingid;
	private String csku;
	private String cimageurl;
	private String coriginalurl;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getClistingid() {
		return clistingid;
	}

	public void setClistingid(String clistingid) {
		this.clistingid = clistingid;
	}

	public String getCsku() {
		return csku;
	}

	public void setCsku(String csku) {
		this.csku = csku;
	}

	public String getCimageurl() {
		return cimageurl;
	}

	public void setCimageurl(String cimageurl) {
		this.cimageurl = cimageurl;
	}

	public String getCoriginalurl() {
		return coriginalurl;
	}

	public void setCoriginalurl(String coriginalurl) {
		this.coriginalurl = coriginalurl;
	}

}

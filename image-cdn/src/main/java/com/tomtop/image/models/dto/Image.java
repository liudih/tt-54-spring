package com.tomtop.image.models.dto;

/**
 * 
 * @author lijun
 *
 */
public class Image {

	private Integer iid;

	private byte[] bcontent;

	private String cpath;

	private String ccontenttype;

	private String cmd5;

	private Integer iwidth;

	private Integer iheight;

	private String cfastdfsurl;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public byte[] getBcontent() {
		return bcontent;
	}

	public void setBcontent(byte[] bcontent) {
		this.bcontent = bcontent;
	}

	public String getCpath() {
		return cpath;
	}

	public void setCpath(String cpath) {
		this.cpath = cpath;
	}

	public String getCcontenttype() {
		return ccontenttype;
	}

	public void setCcontenttype(String ccontenttype) {
		this.ccontenttype = ccontenttype;
	}

	public String getCmd5() {
		return cmd5;
	}

	public void setCmd5(String cmd5) {
		this.cmd5 = cmd5;
	}

	public Integer getIwidth() {
		return iwidth;
	}

	public void setIwidth(Integer iwidth) {
		this.iwidth = iwidth;
	}

	public Integer getIheight() {
		return iheight;
	}

	public void setIheight(Integer iheight) {
		this.iheight = iheight;
	}

	public String getCfastdfsurl() {
		return cfastdfsurl;
	}

	public void setCfastdfsurl(String cfastdfsurl) {
		this.cfastdfsurl = cfastdfsurl;
	}

}

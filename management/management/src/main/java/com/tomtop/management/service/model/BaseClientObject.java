package com.tomtop.management.service.model;

import java.sql.Timestamp;

import com.tomtop.management.ebean.manage.model.PlatForm;
import com.tomtop.management.ebean.manage.model.Site;

/**
 * 
 * @ClassName: BaseClientObject
 * @Description: 客户端业务实体
 * @author liuxin
 * @date 2015年12月16日
 *
 */
public class BaseClientObject {

	private String name;
	private Integer platform;
	private String remark;
	private Integer site_id;
	private Integer is_enabled;
	private Integer is_deleted;
	private Long id;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;
	private String createTime;
	private String updateTime;
	private Site site;
	private PlatForm parameter;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSite_id() {
		return site_id;
	}

	public void setSite_id(Integer site_id) {
		this.site_id = site_id;
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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public PlatForm getParameter() {
		return parameter;
	}

	public void setParameter(PlatForm parameter) {
		this.parameter = parameter;
	}
}

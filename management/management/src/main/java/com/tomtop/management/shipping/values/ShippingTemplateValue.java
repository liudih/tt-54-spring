package com.tomtop.management.shipping.values;

import java.sql.Timestamp;

/**
 * 
 * @ClassName: ShippingTemplateObject
 * @Description: TODO(物流模块定义服务实体类)
 * @author Guozy
 * @date 2016年2月19日
 *
 */
public class ShippingTemplateValue {
	// 编号
	private Long id;
	// 模块名称
	private String template_name;
	// 状态
	private Integer is_enabled;
	// 是否删除
	private Integer is_deleted;
	// 创建时间
	private Timestamp whenCreated;
	// 修改时间
	private Timestamp whenModified;
	// 创建人
	private String whoCreated;
	// 修改人
	private String whoModified;

	private String crateTime;
	private String updateTime;
	private String EnabledStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
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

	public String getEnabledStatus() {
		return EnabledStatus;
	}

	public void setEnabledStatus(String enabledStatus) {
		EnabledStatus = enabledStatus;
	}

}

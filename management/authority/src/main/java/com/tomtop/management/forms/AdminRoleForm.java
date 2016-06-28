package com.tomtop.management.forms;

import java.sql.Timestamp;

public class AdminRoleForm {
	
	private Long id;
	
	private String roleName;

	private String status;
	
	Timestamp whenCreatedStart; //创建时间，用于查询

	Timestamp whenCreatedEnd; //创建时间，用于查询
	
	Timestamp whenModifiedStart;//更新时间，用于查询
	
	Timestamp whenModifiedEnd;//更新时间，用于查询
	
	String whenCreated; //创建时间
	
	String whenModified;//更新时间
	
	String whoCreated; //创建人

	String whoModified; //更新人

	public String getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(String whenCreated) {
		this.whenCreated = whenCreated;
	}

	public String getWhenModified() {
		return whenModified;
	}

	public void setWhenModified(String whenModified) {
		this.whenModified = whenModified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getWhenCreatedStart() {
		return whenCreatedStart;
	}

	public void setWhenCreatedStart(Timestamp whenCreatedStart) {
		this.whenCreatedStart = whenCreatedStart;
	}

	public Timestamp getWhenCreatedEnd() {
		return whenCreatedEnd;
	}

	public void setWhenCreatedEnd(Timestamp whenCreatedEnd) {
		this.whenCreatedEnd = whenCreatedEnd;
	}

	public Timestamp getWhenModifiedStart() {
		return whenModifiedStart;
	}

	public void setWhenModifiedStart(Timestamp whenModifiedStart) {
		this.whenModifiedStart = whenModifiedStart;
	}

	public Timestamp getWhenModifiedEnd() {
		return whenModifiedEnd;
	}

	public void setWhenModifiedEnd(Timestamp whenModifiedEnd) {
		this.whenModifiedEnd = whenModifiedEnd;
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

}

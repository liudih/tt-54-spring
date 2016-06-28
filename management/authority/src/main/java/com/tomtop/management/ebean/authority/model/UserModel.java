package com.tomtop.management.ebean.authority.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhenModified;
import com.avaje.ebean.annotation.WhoCreated;
import com.avaje.ebean.annotation.WhoModified;

@MappedSuperclass
public class UserModel extends Model {

	public static EbeanServer db() {
		return Ebean.getServer("userCenter");
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	Long id;

	@WhenCreated
	@Column(name = "create_date")
	Timestamp whenCreated;

	@WhenModified
	@Column(name = "update_date")
	Timestamp whenModified;

	@WhoCreated
	@Column(name = "create_user")
	String whoCreated;

	@WhoModified
	@Column(name = "update_user")
	String whoModified;

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
	
}

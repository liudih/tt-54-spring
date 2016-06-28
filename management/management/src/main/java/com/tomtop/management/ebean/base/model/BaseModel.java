package com.tomtop.management.ebean.base.model;

import javax.persistence.MappedSuperclass;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Model;

@MappedSuperclass
public class BaseModel extends Model {
	public static EbeanServer db() {
		return Ebean.getServer("base");
	}
}

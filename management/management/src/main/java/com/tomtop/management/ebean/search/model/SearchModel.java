package com.tomtop.management.ebean.search.model;

import javax.persistence.MappedSuperclass;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Model;

@MappedSuperclass
public class SearchModel extends Model {

	public static EbeanServer db() {
		return Ebean.getServer("search");
	}
}

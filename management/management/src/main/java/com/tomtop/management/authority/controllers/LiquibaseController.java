package com.tomtop.management.authority.controllers;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tomtop.management.authority.services.serviceImp.LiquibaseService;

@Controller
public class LiquibaseController {

	private static Logger log = Logger.getLogger(LiquibaseController.class
			.getName());
	
	private static final  String sourceName = "userCenterManagement";
	@Autowired
	LiquibaseService service;

	@Autowired
	@Qualifier("userCenterdb")
	DataSource dataSource;

	@Value("${liquibase.reset}")
	private boolean liquibaseSwitch;

	@RequestMapping(value = "/public/liquibase/checkLiquibase", method = RequestMethod.GET)
	public String checkLiquibase(Map<String, Object> model) throws Exception {
		return "authorityLiquibase";
	}

	@RequestMapping(value = "/public/liquibase/update", method = RequestMethod.GET)
	public String update(Map<String, Object> model, final boolean doit,
			final boolean fixChecksum, String contextParams)
			throws LiquibaseException {
		if (StringUtils.isEmpty(contextParams)) {
			contextParams = "production";
		}
		model.put("resetSwitch", liquibaseSwitch);
		final Set<String> names = Sets.newHashSet();
		names.add(sourceName);
		final String context = contextParams;
		if (doit) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			final String lasttag = "last";
			final String tag = df.format(new Date());
			Map<String, String> output = Maps.toMap(names,
					new Function<String, String>() {
						@Override
						public String apply(String name) {
							try {
								Liquibase liquibase = service
										.getLiquibaseInstance(name);
								if (fixChecksum) {
									liquibase.clearCheckSums();
								}
								liquibase.tag(lasttag);
								liquibase.update(context);
								liquibase.tag(tag);
								return "OK";
							} catch (LiquibaseException e) {
								log.error("Database Change Error", e);
								return e.getMessage();
							}
						}
					});
			model.put("update", output);
			return "authorityLiquibase";
		} else {
			Map<String, StringWriter> output = Maps.toMap(names,
					new Function<String, StringWriter>() {
						@Override
						public StringWriter apply(String name) {
							try {
								Liquibase liquibase = service
										.getLiquibaseInstance(name);
								if (fixChecksum) {
									liquibase.clearCheckSums();
								}
								StringWriter writer = new StringWriter();
								liquibase.update(context, writer);
								return writer;
							} catch (LiquibaseException e) {
								log.error("Database Change Preview Error", e);
								StringWriter sw = new StringWriter();
								sw.append(e.getMessage());
								return sw;
							}
						}
					});
			model.put("updateCheck", output);
			
			return "authorityLiquibase";
		}
	}

	/*
	 * ///liquibase/rollback/:tag
	 * 
	 * @RequestMapping(value = "/public/liquibase/rollback", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public String rollback(final String tag, final boolean
	 * doit, String contextParams) throws Exception {
	 * 
	 * final String
	 * context=StringUtils.isEmpty(contextParams)?"production":contextParams;
	 * final Set<String> names =Sets.newHashSet();
	 * names.add("userCenterManagement"); Map<String, Object>
	 * model=Maps.newHashMap(); if (doit) { Map<String, String> output =
	 * Maps.toMap(names, new Function<String, String>() {
	 * 
	 * @Override public String apply(String name) { try { Liquibase liquibase =
	 * service .getLiquibaseInstance(name); liquibase.rollback(tag, context);
	 * return "OK"; } catch (LiquibaseException e) {
	 * log.error("Database Change Error", e); return e.getMessage(); } } });
	 * return JSON.toJSONString(output); } else { Map<String, StringWriter>
	 * output = Maps.toMap(names, new Function<String, StringWriter>() {
	 * 
	 * @Override public StringWriter apply(String name) { try { Liquibase
	 * liquibase = service .getLiquibaseInstance(name); StringWriter writer =
	 * new StringWriter(); liquibase.rollback(tag, context, writer); return
	 * writer; } catch (LiquibaseException e) {
	 * log.error("Database Change Preview Error", e); StringWriter sw = new
	 * StringWriter(); sw.append(e.getMessage()); return sw; } } }); return
	 * "authorityLiquibase"; } }
	 */
	// reset
	@RequestMapping(value = "/public/liquibase/{dropOnly}", method = RequestMethod.GET)
	public String destructiveUpdate(Map<String, Object> model,
			@PathVariable(value = "dropOnly") boolean dropOnly)
			throws Exception {
		if (!liquibaseSwitch) {
			model.put("reset", "Not Allowed Execution");
		} else {

			final Set<String> names = Sets.newHashSet();
			names.add(sourceName);
			for (String item : names) {
				if (!dropOnly) {
					try {
						service.getLiquibaseInstance(item).dropAll();
						service.getLiquibaseInstance(item).update("test");
						model.put("reset", "success");
					} catch (Exception e) {
						log.error("destructiveUpdate error", e);
						model.put("reset", e.getMessage());
					}
				}
			}
		}
		return "authorityLiquibase";
	}
}
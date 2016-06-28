package com.tomtop.image.configuration;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 * 
 * @author lijun
 *
 */
@ConfigurationProperties(value = "fastdfs")
public class FastdfsSettings {

	private static Logger logger = LoggerFactory
			.getLogger(FastdfsSettings.class);

	private Double threadNum;
	private String xy;
	private JSONArray ja;

	String chicuuXy;
	private JSONArray chicuuJa;
	public List<String> chicuuXyList = Lists.newLinkedList();

	private List<String> xyList = Lists.newLinkedList();

	public FastdfsSettings() {

	}

	public Double getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(Double threadNum) {
		this.threadNum = threadNum;
	}

	public String getXy() {
		return xy;
	}

	public void setXy(String xy) {
		if (StringUtils.isEmpty(xy)) {
			return;
		}
		ja = JSONArray.parseArray(xy);
		for (int i = 0; i < ja.size(); i++) {
			JSONObject jb = ja.getJSONObject(i);
			Integer x = jb.getInteger("x");
			Integer y = jb.getInteger("y");
			if (x != null && x > 0 & y != null & y > 0) {
				this.xyList.add(x + "x" + y);
			}
		}
		this.xy = xy;
	}

	/**
	 * 判断该尺寸的图片是否合法
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean isValidImg(int width, int height) {
		if (2000 == width && 2000 == height) {
			return true;
		}
		String key = width + "x" + height;
		return this.xyList.contains(key);
	}

	public List<String> getXyList() {
		return xyList;
	}

	public JSONArray getJa() {
		return ja;
	}

	public String getChicuuXy() {
		return chicuuXy;
	}

	public void setChicuuXy(String chicuuXy) {
		if (StringUtils.isEmpty(chicuuXy)) {
			return;
		}
		this.chicuuJa = JSONArray.parseArray(chicuuXy);
		for (int i = 0; i < chicuuJa.size(); i++) {
			JSONObject jb = chicuuJa.getJSONObject(i);
			Integer x = jb.getInteger("x");
			Integer y = jb.getInteger("y");
			if (x != null && x > 0 & y != null & y > 0) {
				this.chicuuXyList.add(x + "x" + x);
			}
		}
		this.chicuuXy = chicuuXy;
	}

	public JSONArray getChicuuJa() {
		return chicuuJa;
	}

	public List<String> getChicuuXyList() {
		return chicuuXyList;
	}

}

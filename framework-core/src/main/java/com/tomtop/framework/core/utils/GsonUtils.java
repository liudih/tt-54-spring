package com.tomtop.framework.core.utils;

import com.google.gson.Gson;

/**
 * Gson工具类
 * 
 * @author liulj
 *
 */
public class GsonUtils {
	private static Gson gson = new Gson();

	/**
	 * 转json字符串
	 * 
	 * @param src
	 * @return
	 */
	public String toJson(Object src) {
		return gson.toJson(src);
	}

	/**
	 * 把json字段串转成对象
	 * 
	 * @param json
	 * @param c
	 * @return
	 */
	public <T> T formJson(String json, Class<T> c) {
		return gson.fromJson(json, c);
	}
}

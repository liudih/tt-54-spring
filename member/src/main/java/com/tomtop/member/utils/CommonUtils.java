package com.tomtop.member.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

	public static final int NOT_DATA_RES = 0;   
	
	public static final int SUCCESS_RES = 1;   
	
	public static final int ERROR_RES = -1;   
	
	public static final String NOT_DATA_MSG_RES = "not data";   
	
	public static final String SUCCESS_MSG_RES = "success"; 
	
	public static final String ERROR_MSG_RES = "error"; 
	
	/**
	 * 验证邮箱地址是否正确 　　
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}

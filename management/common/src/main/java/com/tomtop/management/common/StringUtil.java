package com.tomtop.management.common;

/**
 * 
 * @ClassName: StringUtil
 * @Description: TODO(字符串工具类)
 * @author yinfei
 * @date 2015年12月18日
 *
 */
public class StringUtil {

	/**
	 * 
	 * @Title: isNotEmpty
	 * @Description: TODO(判断是否为非空字符串)
	 * @param @param string
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月18日
	 */
	public static boolean isNotEmpty(String string) {
		if (null != string && !string.equals("")) {
			return true;
		} else {
			return false;
		}
	}

}

package com.tomtop.framework.core.utils;

import org.dozer.DozerBeanMapper;

/**
 * bean工具类，实现bean复制等，使用，dozer工具
 * 
 * @author liulj
 *
 */

public class BeanUtils {
	public final static DozerBeanMapper beanMapper = new DozerBeanMapper();

	/**
	 * 根class映射复制对象的属性，生成新对象
	 * 
	 * @param source
	 *            被映射的对象
	 * @param destclas
	 *            映射的class
	 * @return 返回复制后的对象
	 */
	public static <T> T mapFromClass(Object source, Class<T> destclas) {
		return beanMapper.map(source, destclas);
	}

	/**
	 * 将对象的属性，复制到那个对象
	 * 
	 * @param source
	 *            源资对象
	 * @param dest
	 *            目标对象
	 */
	public static void copyPropertys(Object source, Object dest) {
		beanMapper.map(source, dest);
	}

	/**
	 * 复制bean
	 * 
	 * @param source
	 *            复制对象
	 * @return 复制后的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cloneObject(T source) {
		return (T) beanMapper.map(source, source.getClass());
	}
}

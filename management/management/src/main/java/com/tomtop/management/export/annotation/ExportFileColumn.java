package com.tomtop.management.export.annotation;

import java.lang.annotation.*;

/**
 * 
 * @ClassName: Annotationmodel
 * @Description: 定义一个注解
 * @author liuxin
 * @date 2016年3月7日
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface ExportFileColumn {

	int priority() default 1;

	/**
	 * 为注解添加属性
	 */
	String name();
	
	String type() default "label";
	
	String item() default "f";

}

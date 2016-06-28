package com.tomtop.product.common.enums;

/**
 * 常量实体类
 * 
 * @author ztiny
 * @Date 2015-12-19
 */
public class CommonEntity {

	// 用于接口调用的状态码
	public enum returnStatus {
		/** 内部错误 */
		INNER_ERROR {
			public String getName() {
				return "500";
			}
		},
		/** 某个字段可能为空 */
		COLUMN_ISNULL {
			public String getName() {
				return "100";
			}
		},
		/** 索引建立成功 */
		SUCESS {
			public String getName() {
				return "200";
			}
		};
		public abstract String getName();
	}
}

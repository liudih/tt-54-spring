package com.tomtop.framework.core.utils;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

/**
 * @ClassName: Result
 * @Description: 返回的结果对象
 * @author 文龙
 * @date 2015-11-6 上午11:25:29
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Result implements Serializable {

	private static final long serialVersionUID = -6888789042963799184L;

	/**
	 * 返回标志 1:成功 0：失败
	 */
	private Integer ret;

	/**
	 * 返回的错误码
	 */
	private String errCode;

	/**
	 * 返回的错误消息
	 */
	private String errMsg;

	/**
	 * 返回的数据
	 */
	private Object data;

	/**
	 * Page页面对象 用于列表分页
	 */
	private Page page;

	public Result() {

	}

	public Result(Integer ret, String errCode, String errMsg) {
		this.ret = ret;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public Result(Integer ret, Object data) {
		this.ret = ret;
		this.data = data;
	}

	public Result(Integer ret, Object data, Page page) {
		this(ret, data);
		this.page = page;
	}

	public Result(Integer ret, String errCode, String errMsg, Object data) {
		this(ret, errCode, errMsg);
		this.data = data;
	}

	public Integer getRet() {
		return ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 成功 SUCCESS = "1"
	 */
	public static final Integer SUCCESS = 1;

	/**
	 * 失败 FAIL = "0"
	 */
	public static final Integer FAIL = 0;

}

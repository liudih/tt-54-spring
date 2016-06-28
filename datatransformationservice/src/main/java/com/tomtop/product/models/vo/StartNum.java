package com.tomtop.product.models.vo;

/**
 * 商品评论平均星级数量
 * 
 * @author ztiny
 * @Date 215-12-25
 */
public class StartNum {

	// 评论星级 1-5星
	private Integer startNum;
	// 百分比
	private Integer ptage;

	public StartNum() {

	}

	public StartNum(Integer startNum, Integer ptage) {
		this.startNum = startNum;
		this.ptage = ptage;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getPtage() {
		return ptage;
	}

	public void setPtage(Integer ptage) {
		this.ptage = ptage;
	}
}

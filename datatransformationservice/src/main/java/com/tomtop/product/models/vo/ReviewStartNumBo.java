package com.tomtop.product.models.vo;

import java.util.List;

/**
 * 商品评论实体类
 * 
 * @author ztiny
 * @Date 215-12-25
 */
public class ReviewStartNumBo {
	// 邮箱评论总数
	private Integer count;
	// 评论星级平均数
	private Double start;
	// 星级对应总评论的总数
	private List<StartNum> startNum;

	public ReviewStartNumBo(Integer count, Double start, List<StartNum> startNum) {
		this.count = count;
		this.start = start;
		this.startNum = startNum;
	}

	public ReviewStartNumBo() {

	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getStart() {
		return start;
	}

	public void setStart(Double start) {
		this.start = start;
	}

	public List<StartNum> getStartNum() {
		return startNum;
	}

	public void setStartNum(List<StartNum> startNum) {
		this.startNum = startNum;
	}
}

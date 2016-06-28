package com.tomtop.management.common;

import java.util.List;

public class Page<T> {
	private int pageNo;
	private int limit;
	private List<T> list;
	private int count;
	private int totalPage;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotalPage() {
		if (count % limit == 0) {
			return count / limit;
		} else {
			return count / limit + 1;
		}
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}

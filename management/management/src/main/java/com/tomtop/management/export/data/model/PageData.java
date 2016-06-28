package com.tomtop.management.export.data.model;

public class PageData {

	private int totalRecord;
	private int totalPage;
	private int startRec;
	private int currentPage;
	private int pageSize;
	private int endRec;

	/*
	 * "totalRecord": 1612, "totalPage": 162, "startRec": 0, "endRec": 10,
	 * "currentPage": 1, "pageSize": 10
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartRec() {
		return startRec;
	}

	public void setStartRec(int startRec) {
		this.startRec = startRec;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getEndRec() {
		return endRec;
	}

	public void setEndRec(int endRec) {
		this.endRec = endRec;
	}
}

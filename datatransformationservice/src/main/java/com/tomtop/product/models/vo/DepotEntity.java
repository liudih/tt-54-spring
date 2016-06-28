package com.tomtop.product.models.vo;

/**
 * 仓库实体类
 * 
 * @author ztiny
 * @Date 2015-12-19
 */
public class DepotEntity {
	// 仓库ID
	private Integer depotid;
	// 仓库名称
	private String depotName;

	public DepotEntity() {

	}

	public DepotEntity(Integer depotid, String depotName) {
		this.depotid = depotid;
		this.depotName = depotName;
	}

	public Integer getDepotid() {
		return depotid;
	}

	public void setDepotid(Integer depotid) {
		this.depotid = depotid;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}
}

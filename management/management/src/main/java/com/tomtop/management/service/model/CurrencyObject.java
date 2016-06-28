package com.tomtop.management.service.model;

import java.sql.Timestamp;

/**
 * 
 * @ClassName: CurrencyObject
 * @Description: TODO(汇率服务实体类)
 * @author Guozy
 * @date 2015年12月22日
 *
 */
public class CurrencyObject {

	private Long id;
	private String name;
	private String code;
	private Integer symbol_positions;
	private String symbol_code;
	private Double current_rate;
	private Double new_rate;
	private Integer decimal_places;
	private Timestamp synchro_date;
	private Integer sort;
	private Integer is_deleted;
	private Integer is_enabled;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;

	private String createTime;
	private String updateTime;
	private String synchroTime;
	private String enabledStatus;
	private String symbolPositionStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSymbol_positions() {
		return symbol_positions;
	}

	public void setSymbol_positions(Integer symbol_positions) {
		this.symbol_positions = symbol_positions;
	}

	public String getSymbol_code() {
		return symbol_code;
	}

	public void setSymbol_code(String symbol_code) {
		this.symbol_code = symbol_code;
	}

	public Double getCurrent_rate() {
		return current_rate;
	}

	public void setCurrent_rate(Double current_rate) {
		this.current_rate = current_rate;
	}

	public Double getNew_rate() {
		return new_rate;
	}

	public void setNew_rate(Double new_rate) {
		this.new_rate = new_rate;
	}

	public Integer getDecimal_places() {
		return decimal_places;
	}

	public void setDecimal_places(Integer decimal_places) {
		this.decimal_places = decimal_places;
	}

	public Timestamp getSynchro_date() {
		return synchro_date;
	}

	public void setSynchro_date(Timestamp synchro_date) {
		this.synchro_date = synchro_date;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public Timestamp getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(Timestamp whenCreated) {
		this.whenCreated = whenCreated;
	}

	public Timestamp getWhenModified() {
		return whenModified;
	}

	public void setWhenModified(Timestamp whenModified) {
		this.whenModified = whenModified;
	}

	public String getWhoCreated() {
		return whoCreated;
	}

	public void setWhoCreated(String whoCreated) {
		this.whoCreated = whoCreated;
	}

	public String getWhoModified() {
		return whoModified;
	}

	public void setWhoModified(String whoModified) {
		this.whoModified = whoModified;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getEnabledStatus() {
		return enabledStatus;
	}

	public void setEnabledStatus(String enabledStatus) {
		this.enabledStatus = enabledStatus;
	}

	public String getSynchroTime() {
		return synchroTime;
	}

	public void setSynchroTime(String synchroTime) {
		this.synchroTime = synchroTime;
	}

	public String getSymbolPositionStr() {
		return symbolPositionStr;
	}

	public void setSymbolPositionStr(String symbolPositionStr) {
		this.symbolPositionStr = symbolPositionStr;
	}

}

package com.tomtop.management.homepage.service.values;

import java.sql.Timestamp;
import java.util.List;

import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;

/**
 * 
    * @ClassName: RecommendValue
    * @Description: 推荐管理业务实体（添加、修改、删除）
    * @author liuxin
    * @date 2016年1月11日
    *
 */
public class RecommendValue {
	private List<Integer> clients;
	private List<Integer> languages;
	private Integer client_id;
	private Integer language_id;
	private String code;// 模块标识
	private String name;// 模块名称
	private String layout_code;// 布局标识
	private Integer layout_id;// 布局id
	private Integer position_id;// 位置
	private String url;// 路由
	private Integer number;// 模块显示的数量
	private Integer sort;// 排序号
	private Integer is_enabled;// 状态
	private Integer is_deleted;
	private Long id;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;
	private Client client;
	private Language language;
	private Layout layout;
	private BaseParameter parameter;
	private String createTime;
	private String updateTime;

	private List<SkuContentValue> skuContentValues;

	public List<Integer> getClients() {
		return clients;
	}

	public void setClients(List<Integer> clients) {
		this.clients = clients;
	}

	public List<Integer> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Integer> languages) {
		this.languages = languages;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLayout_code() {
		return layout_code;
	}

	public void setLayout_code(String layout_code) {
		this.layout_code = layout_code;
	}

	public Integer getLayout_id() {
		return layout_id;
	}

	public void setLayout_id(Integer layout_id) {
		this.layout_id = layout_id;
	}

	public Integer getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Integer position_id) {
		this.position_id = position_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public BaseParameter getParameter() {
		return parameter;
	}

	public void setParameter(BaseParameter parameter) {
		this.parameter = parameter;
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

	public List<SkuContentValue> getSkuContentValues() {
		return skuContentValues;
	}

	public void setSkuContentValues(List<SkuContentValue> skuContentValues) {
		this.skuContentValues = skuContentValues;
	}

	public Integer getClient_id() {
		return client_id;
	}

	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}
}

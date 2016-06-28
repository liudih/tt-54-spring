package com.tomtop.management.service.model;

import java.sql.Timestamp;
import java.util.List;

import com.tomtop.management.ebean.homepage.model.FeaturedCategoryBanner;
import com.tomtop.management.ebean.homepage.model.FeaturedCategoryKey;
import com.tomtop.management.ebean.homepage.model.FeaturedCategorySku;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Category;

/**
 * 
 * @ClassName: FeaturedCategoryObject
 * @Description: TODO(特色类目服务实体类)
 * @author Guozy
 * @date 2015年12月25日
 *
 */
public class FeaturedCategoryObject {

	private Long id;
	private Integer client_id;
	private Integer language_id;
	private Integer number;
	private Integer category_id;
	private String img_url;
	private Integer sort;
	private Integer is_deleted;
	private Integer is_enabled;
	private Timestamp whenCreated;
	private Timestamp whenModified;
	private String whoCreated;
	private String whoModified;

	private Client client;
	private Language language;
	private Category category;
	private String createTime;
	private String updateTime;
	private String enabledStatus;
	private String startTime;
	
	private List<FeaturedCategorySkuObject> skus;
	

	private List<FeaturedCategorySku> featuredCategorySkus;
	private List<FeaturedCategoryKey> featuredCategoryKeys;
	private List<FeaturedCategoryBanner> featuredCategoryBanners;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public List<FeaturedCategorySku> getFeaturedCategorySkus() {
		return featuredCategorySkus;
	}

	public void setFeaturedCategorySkus(List<FeaturedCategorySku> featuredCategorySkus) {
		this.featuredCategorySkus = featuredCategorySkus;
	}

	public List<FeaturedCategoryKey> getFeaturedCategoryKeys() {
		return featuredCategoryKeys;
	}

	public void setFeaturedCategoryKeys(List<FeaturedCategoryKey> featuredCategoryKeys) {
		this.featuredCategoryKeys = featuredCategoryKeys;
	}

	public List<FeaturedCategoryBanner> getFeaturedCategoryBanners() {
		return featuredCategoryBanners;
	}

	public void setFeaturedCategoryBanners(List<FeaturedCategoryBanner> featuredCategoryBanners) {
		this.featuredCategoryBanners = featuredCategoryBanners;
	}

}

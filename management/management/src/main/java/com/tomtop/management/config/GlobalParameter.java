package com.tomtop.management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalParameter {

	/**
	 * 外部样式、js变量
	 */
	@Value("${style.url}")
	private String styleUrl;

	/**
	 * 图片路径
	 */
	@Value("${image.upload.url}")
	private String imageUploadUrl;

	@Value("${image.upload.token}")
	private String imageUploadToken;

	/**
	 * base 模块清楚缓存url
	 */
	@Value("${cache.base.clear.url}")
	private String cacheBaseClearUrl;

	/**
	 * 更新价格缓存
	 */
	@Value("${cache.price.clear.url}")
	private String cachePriceClearUrl;

	/**
	 * product 模块清楚缓存url
	 */
	@Value("${cache.product.clear.url}")
	private String cacheProductClearUrl;
	/**
	 * advert 模块清楚缓存url
	 */
	@Value("${cache.advert.clear.url}")
	private String cacheAdvertClearUrl;

	/**
	 * 计算运费接口url
	 */
	@Value("${shipping.freight.url}")
	private String shippingFreightUrl;

	/**
	 * 对接搜索（根据品类Id）
	 */
	@Value("${search.url}")
	private String searchUrl;
	/**
	 * 计算运费接口token
	 */
	@Value("${shipping.freight.token}")
	private String shippingFreightToken;

	/**
	 * mongoDB
	 */
	@Value("${mongo.ip}")
	private String mongodbIp;
	@Value("${mongo.port}")
	private String mongodbPort;
	@Value("${mongo.database}")
	private String mongodbDataBase;
	@Value("${mongo.user}")
	private String mongodbUser;
	@Value("${mongo.password}")
	private String mongodbPassword;

	public String getStyleUrl() {
		return styleUrl;
	}

	public String getImageUploadUrl() {
		return imageUploadUrl;
	}

	public String getImageUploadToken() {
		return imageUploadToken;
	}

	public String getCacheBaseClearUrl() {
		return cacheBaseClearUrl;
	}

	public String getCacheProductClearUrl() {
		return cacheProductClearUrl;
	}

	public String getCacheAdvertClearUrl() {
		return cacheAdvertClearUrl;
	}

	public String getCachePriceClearUrl() {
		return cachePriceClearUrl;
	}

	public String getShippingFreightUrl() {
		return shippingFreightUrl;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public String getShippingFreightToken() {
		return shippingFreightToken;
	}

	public String getMongodbIp() {
		return mongodbIp;
	}

	public String getMongodbPort() {
		return mongodbPort;
	}

	public String getMongodbDataBase() {
		return mongodbDataBase;
	}

	public String getMongodbUser() {
		return mongodbUser;
	}

	public String getMongodbPassword() {
		return mongodbPassword;
	}

}

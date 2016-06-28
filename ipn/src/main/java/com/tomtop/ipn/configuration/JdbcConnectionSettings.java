package com.tomtop.ipn.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "jdbc")
public class JdbcConnectionSettings extends AbstractSettings {

	private String driver;
	private String url;
	private String username;
	private String password;
	private Integer idleConnectionTestPeriod;
	private Integer idleMaxAge;
	private Integer minConnectionsPerPartition;
	private Integer maxConnectionsPerPartition;
	private Integer partitionCount;
	private Integer acquireIncrement;
	private Integer statementsCacheSize;
	private Integer releaseHelperThreads;

	private String orderDriver;
	private String orderUrl;
	private String orderUsername;
	private String orderPassword;
	private Integer orderMinConnectionsPerPartition;
	private Integer orderMaxConnectionsPerPartition;

	private String paypalDriver;
	private String paypalUrl;
	private String paypalUsername;
	private String paypalPassword;
	private Integer paypalMinConnectionsPerPartition;
	private Integer paypalMaxConnectionsPerPartition;

	private String baseDriver;
	private String baseUrl;
	private String baseUsername;
	private String basePassword;
	private Integer baseMinConnectionsPerPartition;
	private Integer baseMaxConnectionsPerPartition;

	public JdbcConnectionSettings() {

	}

	public String getOrderDriver() {
		return orderDriver;
	}

	public void setOrderDriver(String orderDriver) {
		this.orderDriver = orderDriver;
	}

	public String getOrderUrl() {
		return orderUrl;
	}

	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}

	public String getOrderUsername() {
		return orderUsername;
	}

	public void setOrderUsername(String orderUsername) {
		this.orderUsername = orderUsername;
	}

	public String getOrderPassword() {
		return orderPassword;
	}

	public void setOrderPassword(String orderPassword) {
		this.orderPassword = orderPassword;
	}

	public String getPaypalDriver() {
		return paypalDriver;
	}

	public void setPaypalDriver(String paypalDriver) {
		this.paypalDriver = paypalDriver;
	}

	public String getPaypalUrl() {
		return paypalUrl;
	}

	public void setPaypalUrl(String paypalUrl) {
		this.paypalUrl = paypalUrl;
	}

	public String getPaypalUsername() {
		return paypalUsername;
	}

	public void setPaypalUsername(String paypalUsername) {
		this.paypalUsername = paypalUsername;
	}

	public String getPaypalPassword() {
		return paypalPassword;
	}

	public void setPaypalPassword(String paypalPassword) {
		this.paypalPassword = paypalPassword;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	public void setIdleConnectionTestPeriod(Integer idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public Integer getIdleMaxAge() {
		return idleMaxAge;
	}

	public void setIdleMaxAge(Integer idleMaxAge) {
		this.idleMaxAge = idleMaxAge;
	}

	public Integer getMinConnectionsPerPartition() {
		return minConnectionsPerPartition;
	}

	public void setMinConnectionsPerPartition(Integer minConnectionsPerPartition) {
		this.minConnectionsPerPartition = minConnectionsPerPartition;
	}

	public Integer getMaxConnectionsPerPartition() {
		return maxConnectionsPerPartition;
	}

	public void setMaxConnectionsPerPartition(Integer maxConnectionsPerPartition) {
		this.maxConnectionsPerPartition = maxConnectionsPerPartition;
	}

	public Integer getPartitionCount() {
		return partitionCount;
	}

	public void setPartitionCount(Integer partitionCount) {
		this.partitionCount = partitionCount;
	}

	public Integer getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(Integer acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public Integer getStatementsCacheSize() {
		return statementsCacheSize;
	}

	public void setStatementsCacheSize(Integer statementsCacheSize) {
		this.statementsCacheSize = statementsCacheSize;
	}

	public Integer getReleaseHelperThreads() {
		return releaseHelperThreads;
	}

	public void setReleaseHelperThreads(Integer releaseHelperThreads) {
		this.releaseHelperThreads = releaseHelperThreads;
	}

	public String getBaseDriver() {
		return baseDriver;
	}

	public void setBaseDriver(String baseDriver) {
		this.baseDriver = baseDriver;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseUsername() {
		return baseUsername;
	}

	public void setBaseUsername(String baseUsername) {
		this.baseUsername = baseUsername;
	}

	public String getBasePassword() {
		return basePassword;
	}

	public void setBasePassword(String basePassword) {
		this.basePassword = basePassword;
	}

	public Integer getOrderMinConnectionsPerPartition() {
		return orderMinConnectionsPerPartition;
	}

	public void setOrderMinConnectionsPerPartition(
			Integer orderMinConnectionsPerPartition) {
		this.orderMinConnectionsPerPartition = orderMinConnectionsPerPartition;
	}

	public Integer getOrderMaxConnectionsPerPartition() {
		return orderMaxConnectionsPerPartition;
	}

	public void setOrderMaxConnectionsPerPartition(
			Integer orderMaxConnectionsPerPartition) {
		this.orderMaxConnectionsPerPartition = orderMaxConnectionsPerPartition;
	}

	public Integer getPaypalMinConnectionsPerPartition() {
		return paypalMinConnectionsPerPartition;
	}

	public void setPaypalMinConnectionsPerPartition(
			Integer paypalMinConnectionsPerPartition) {
		this.paypalMinConnectionsPerPartition = paypalMinConnectionsPerPartition;
	}

	public Integer getPaypalMaxConnectionsPerPartition() {
		return paypalMaxConnectionsPerPartition;
	}

	public void setPaypalMaxConnectionsPerPartition(
			Integer paypalMaxConnectionsPerPartition) {
		this.paypalMaxConnectionsPerPartition = paypalMaxConnectionsPerPartition;
	}

	public Integer getBaseMinConnectionsPerPartition() {
		return baseMinConnectionsPerPartition;
	}

	public void setBaseMinConnectionsPerPartition(
			Integer baseMinConnectionsPerPartition) {
		this.baseMinConnectionsPerPartition = baseMinConnectionsPerPartition;
	}

	public Integer getBaseMaxConnectionsPerPartition() {
		return baseMaxConnectionsPerPartition;
	}

	public void setBaseMaxConnectionsPerPartition(
			Integer baseMaxConnectionsPerPartition) {
		this.baseMaxConnectionsPerPartition = baseMaxConnectionsPerPartition;
	}

}

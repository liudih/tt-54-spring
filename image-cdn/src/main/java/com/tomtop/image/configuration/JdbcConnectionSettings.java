package com.tomtop.image.configuration;

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

	private String productDriver;
	private String productUrl;
	private String productUsername;
	private String productPassword;

	public JdbcConnectionSettings() {

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

	public String getProductDriver() {
		return productDriver;
	}

	public void setProductDriver(String productDriver) {
		this.productDriver = productDriver;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getProductUsername() {
		return productUsername;
	}

	public void setProductUsername(String productUsername) {
		this.productUsername = productUsername;
	}

	public String getProductPassword() {
		return productPassword;
	}

	public void setProductPassword(String productPassword) {
		this.productPassword = productPassword;
	}

}

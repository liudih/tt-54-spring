package com.tomtop.ipn.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author lijun
 *
 */
@ConfigurationProperties(value = "ipn")
public class IpnSettings {

	private static final String SANDBOX_ENDPOINT = "https://www.sandbox.paypal.com/cgi-bin/webscr";
	private static final String ENDPOINT = "https://www.paypal.com/cgi-bin/webscr";

	private boolean sandbox;
	private String oceanSignValueUrl;
	private String sendEventUrl;

	public IpnSettings() {

	}

	public boolean isSandbox() {
		return sandbox;
	}

	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}

	public String getEndpoint() {
		if (sandbox) {
			return SANDBOX_ENDPOINT;
		} else {
			return ENDPOINT;
		}
	}

	public String getOceanSignValueUrl() {
		return oceanSignValueUrl;
	}

	public void setOceanSignValueUrl(String oceanSignValueUrl) {
		this.oceanSignValueUrl = oceanSignValueUrl;
	}

	public String getSendEventUrl() {
		return sendEventUrl;
	}

	public void setSendEventUrl(String sendEventUrl) {
		this.sendEventUrl = sendEventUrl;
	}

}

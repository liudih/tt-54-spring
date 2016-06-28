package com.tomtop.member.service;

import com.tomtop.member.models.bo.BaseBo;
import com.tomtop.member.models.other.FaceBookUser;
import com.tomtop.member.models.other.GoogleUser;
import com.tomtop.member.models.other.PaypalUser;

public interface IMemberOtherService {

	public BaseBo loginOrRegistration(String email,String source,String sourceId,
			Integer siteId,String country);
	
	public FaceBookUser getFaceBookUser(String token);
	
	public String getFaceBookAccessToken(String code, String returnUrl,
			String appId,String appSecret);
	
	public GoogleUser getGoogleUser(String token);
	
	public String getGoogleAccessToken(String code, String returnUrl,
			String appId,String appSecret);
	
	public PaypalUser getPaypalUser(String token);
	
	public String getPaypalAccessToken(String code, String returnUrl,
			String appId,String appSecret);
	
}

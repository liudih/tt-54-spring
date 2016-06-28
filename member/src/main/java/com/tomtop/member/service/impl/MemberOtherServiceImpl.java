package com.tomtop.member.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomtop.member.mappers.MemberBaseMapper;
import com.tomtop.member.mappers.MemberOtherMapper;
import com.tomtop.member.models.bo.BaseBo;
import com.tomtop.member.models.bo.RegisterMemberBo;
import com.tomtop.member.models.dto.MemberBase;
import com.tomtop.member.models.dto.MemberOtherId;
import com.tomtop.member.models.other.FaceBookUser;
import com.tomtop.member.models.other.GoogleUser;
import com.tomtop.member.models.other.PaypalUser;
import com.tomtop.member.service.IMemberOtherService;
import com.tomtop.member.utils.ClientUtil;
import com.tomtop.member.utils.CommonUtils;

@Service
public class MemberOtherServiceImpl implements IMemberOtherService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MemberBaseMapper memberBaseMapper;
	@Autowired
	MemberOtherMapper memberOtherMapper;
	@Autowired
	MemberServiceImpl memberService;
	
	private final static String endpoint = "api.sandbox.paypal.com";
	//private final static String endpoint = "api.paypal.com";
	
	/**
	 * 第三方登陆时操作
	 * @param email
	 * 		                邮箱(必填)
	 * @param source
	 * 		               来源(必填)
	 * @param sourceId
	 * 		               账号Id(必填)
	 * @param siteId
	 * 		               站点Id(默认为1)
	 * @param country
	 * 		              国家
	 * 
	 * @return Boolean
	 * 
	 * @author renyy
	 */
	@Override
	public BaseBo loginOrRegistration(String email, String source,
			String sourceId,Integer siteId,String country) {
		BaseBo bb = new BaseBo();
		if(email == null || "".equals(email)){
			bb.setRes(-1014);
			bb.setMsg("loginOrRegistration email is null");
			return bb;
		}
		if(source == null){
			bb.setRes(-1015);
			bb.setMsg("loginOrRegistration source is null");
			return bb;
		}
		if(sourceId == null){
			bb.setRes(-1016);
			bb.setMsg("loginOrRegistration sourceId is null");
			return bb;
		}
		if(siteId == null){
			siteId = 1;
		}
		if(country == null){
			country = "US";
		}
		MemberOtherId otherId = memberOtherMapper.getBySource(source,
				sourceId);
		MemberBase mb = null;
		//用户
		if (otherId != null) {
			MemberBase mbw = new MemberBase();
			mbw.setCemail(email);
			mbw.setIwebsiteid(siteId);
			mb = memberBaseMapper.getMemberBaseWhere(mbw);
		}
		if (mb != null) {
			//用户已登陆注册过
			bb.setRes(CommonUtils.SUCCESS_RES);
			return bb;
		}else{
			MemberOtherId other = new MemberOtherId();
			other.setBvalidated(false);
			other.setCemail(email);
			other.setCsource(source);
			other.setCsourceid(sourceId);
			memberOtherMapper.insert(other);
			logger.debug("Member {} Registration done by {}", email,
					source);
			//用户注册
			RegisterMemberBo rmb = memberService.memberRegister(email, sourceId, country, siteId, source);
			if(rmb.getRes() != CommonUtils.SUCCESS_RES){
				bb.setRes(rmb.getRes());
				bb.setMsg(rmb.getMsg());
				return rmb;
			}
		}
		bb.setRes(CommonUtils.SUCCESS_RES);
		return bb;
	}

	/**
	 * 获取Facebook用户信息
	 * 
	 *  @param token
	 */
	public FaceBookUser getFaceBookUser(String token) {
		String uri = "https://graph.facebook.com/v2.2/me?"
				+ "fields=id,name,email,first_name,middle_name,last_name,gender,"
				+ "link,timezone,updated_time,verified&" + token;
		String feedback = ClientUtil.getRequest(uri);
		if(feedback == null){
			return null;
		}
		JSONObject object = JSON.parseObject(feedback);
		FaceBookUser fbu = new FaceBookUser();
		
		String id = (String) object.get("id");
		String name = (String) object.get("name");
		String email = (String) object.get("email");
		String firstName = (String) object.get("first_name");
		String lastName = (String) object.get("last_name");
		String gender = (String) object.get("gender");
		String link = (String) object.get("link");
		Integer timezone = (Integer) object.get("timezone");
		Boolean verified = (Boolean) object.get("verified");
		
		fbu.setId(id);
		fbu.setName(name);
		fbu.setEmail(email);
		fbu.setFirstName(firstName);
		fbu.setLastName(lastName);
		fbu.setGender(gender);
		fbu.setLink(link);
		fbu.setTimezone(timezone);
		fbu.setVerified(verified);
		
		return fbu;
	}
	/**
	 * 获取FaceBook Token
	 * 
	 *  @param token
	 *  
	 *  @param returnUrl
	 *  		返回地址
	 *  @param appId
	 *  		交互id
	 *  @param appSecret
	 *  		秘钥
	 */
	public String getFaceBookAccessToken(String code, String returnUrl,
			String appId,String appSecret) {
		try {
			String url = "https://graph.facebook.com/oauth/access_token?client_id="
					+ appId
					+ "&client_secret="
					+ appSecret
					+ "&code="
					+ code
					+ "&redirect_uri=" + URLEncoder.encode(returnUrl, "UTF-8");
			String body = ClientUtil.getRequest(url);
			if(body == null){
				return null;
			}
			body.replaceFirst("access_token=([^&]*).*$", "$1");
			return body;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happened", e);
		}
	}
	
	/**
	 * 获取Google用户信息
	 * 
	 *  @param token
	 */
	public GoogleUser getGoogleUser(String token) {
		String uri = "https://www.googleapis.com/plus/v1/people/me?access_token=" + token;
		String feedback = ClientUtil.getRequest(uri);
		if(feedback == null){
			return null;
		}
		JSONObject object = JSON.parseObject(feedback);
		GoogleUser gu = JSONObject.toJavaObject(object, GoogleUser.class);
		return gu;
	}
	
	/**
	 * 获取Google token
	 * 
	 *  @param token
	 *  
	 *  @param returnUrl
	 *  		返回地址
	 *  @param appId
	 *  		交互id
	 *  @param appSecret
	 *  		秘钥
	 */
	public String getGoogleAccessToken(String code, String returnUrl,
			String appId,String appSecret) {
			String url = "https://www.googleapis.com/oauth2/v3/token";
			String param = "client_id=" + appId
					+ "&client_secret="+ appSecret
					+ "&code="+ code
					+ "&redirect_uri=" + returnUrl
					+ "&grant_type=authorization_code";
			String body = ClientUtil.postUrl(url, param);
			if(body == null){
				return null;
			}
			JSONObject object = JSON.parseObject(body);
			String token = (String) object.get("access_token");
			return token;
	}
	
	/**
	 * 获取Paypal用户信息
	 * 
	 *  @param token
	 */
	@Override
	public PaypalUser getPaypalUser(String token) {
		String url = "https://" + endpoint
				+ "/v1/identity/openidconnect/userinfo?schema=openid";
		String headerValue = "Bearer " + token;
		
		String feedback = ClientUtil.paypalGet(url,headerValue);
		if(feedback == null){
			return null;
		}
		JSONObject object = JSON.parseObject(feedback);
		PaypalUser pu = new PaypalUser();
		String email = (String) object.get("email");
		String userId = (String) object.get("user_id");
		pu.setEmail(email);
		pu.setUserId(userId);
		
		return pu;
	}
	
	/**
	 * 获取Paypal token
	 * 
	 *  @param token
	 *  
	 *  @param returnUrl
	 *  		返回地址
	 *  @param appId
	 *  		交互id
	 *  @param appSecret
	 *  		秘钥
	 */
	@Override
	public String getPaypalAccessToken(String code, String returnUrl,
			String appId, String appSecret) {
		try {
		String url = "https://" + endpoint
				+ "/v1/identity/openidconnect/tokenservice?client_id="
				+ appId + "&client_secret=" + appSecret
				+ "&grant_type=authorization_code&code=" + code
				+ "&redirect_uri=" + URLEncoder.encode(returnUrl, "UTF-8");
		String body = ClientUtil.getRequest(url);
		if(body == null){
			return null;
		}
		JSONObject object = JSON.parseObject(body);
		String token = (String) object.get("access_token");
		
		return token;
		
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happened", e);
		}
	}
}

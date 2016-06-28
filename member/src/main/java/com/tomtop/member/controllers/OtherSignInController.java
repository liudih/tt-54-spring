package com.tomtop.member.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tomtop.framework.core.utils.Result;
import com.tomtop.member.models.bo.OtherSignBo;
import com.tomtop.member.models.other.FaceBookUser;
import com.tomtop.member.models.other.GoogleUser;
import com.tomtop.member.models.other.PaypalUser;
import com.tomtop.member.service.IMemberOtherService;
import com.tomtop.member.utils.CommonUtils;

/**
 * 第三方登陆操作类
 * 
 * @author renyy
 *
 */
@RestController
@RequestMapping(value = "/other")
public class OtherSignInController {

	private static String facebookSign = "https://www.facebook.com/dialog/oauth";
	
	private static String googleSign = "https://accounts.google.com/o/oauth2/auth";
	
	private static String paypalSign = "https://www.sandbox.paypal.com/webapps/auth/protocol/openidconnect/v1/authorize";
	//private static String paypalSign = "https://www.paypal.com/webapps/auth/protocol/openidconnect/v1/authorize";
	
	private final static String FACEBOOK_APP_ID = "826674510776293";
	private final static String FACEBOOK_APP_SECRET = "843401fe146007480d69092039bd4881";
	
	private final static String GOOGLE_APP_ID = "438380739349-aic3n3kmkb4o2uio19675n0sqj9r1v9r.apps.googleusercontent.com";
	private final static String GOOGLE_APP_SECRET = "Gp_O1Lf075ZmGBX_IxcA7Avt";
	
	private final static String PAYPAL_APP_ID = "AUOCRsoRDtP7rDk3jYVh_YQrsiTZoS_YSFqcvZKwGvjAuX7yQRVU-dfK470WcjX7UZB1j2xoz9AmBsRx";
	private final static String PAYPAL_APP_SECRET = "ELdczd7lgFLQDLGSL4QXK7G_m_FhuOnvcSwIRCHzpBX6G4w-dyx10E3VknMOZsMH_ueuq_AH9ZxaSsNw"; 
			
	private final static String PAYPAL_TYPE = "paypal";
	private final static String GOOGLE_TYPE = "google";
	private final static String FACEBOOK_TYPE = "facebook";
	private final static String CODE = "code";
	private final static String STATE = "state";
	
	@Autowired
	IMemberOtherService memberOtherService;
	
	/**
	 * 获取第三方登陆链接
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/v1/signIn")
	public Result getOtherSignInLink(){
		Result res = new Result();
		List<OtherSignBo> osbList = new ArrayList<OtherSignBo>();
		//创建facebook链接
		StringBuffer sb = new StringBuffer(facebookSign);
		sb.append("?client_id=");sb.append(FACEBOOK_APP_ID);
		sb.append("&redirect_uri=");sb.append("http://127.0.0.1:9003/other/facebook");
		sb.append("&response_type=");sb.append(CODE);
		sb.append("&scope=");sb.append("email");
		OtherSignBo osb = new OtherSignBo();
		osb.setUrl(sb.toString());
		osb.setType(FACEBOOK_TYPE);
		osbList.add(osb);
		//创建google链接
		sb = new StringBuffer(googleSign);
		sb.append("?client_id=");sb.append(GOOGLE_APP_ID);
		sb.append("&redirect_uri=");sb.append("http://127.0.0.1:9003/other/google");
		sb.append("&response_type=");sb.append(CODE);
		sb.append("&state=");sb.append(STATE);
		sb.append("&scope=");sb.append("https://www.googleapis.com/auth/plus.login email");
		osb = new OtherSignBo();
		osb.setUrl(sb.toString());
		osb.setType(GOOGLE_TYPE);
		osbList.add(osb);
		//创建paypal链接
		sb = new StringBuffer(paypalSign);
		sb.append("?client_id=");sb.append(PAYPAL_APP_ID);
		sb.append("&redirect_uri=");sb.append("http://127.0.0.1:9003/other/paypal");
		sb.append("&response_type=");sb.append(CODE);
		sb.append("&scope=");sb.append("openid email");
		osb = new OtherSignBo();
		osb.setUrl(sb.toString());
		osb.setType(PAYPAL_TYPE);
		osbList.add(osb);
		
		res.setRet(CommonUtils.SUCCESS_RES);
		res.setData(osbList);
		
		return res;
	}
	
	/**
	 * Facebook回调
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/facebook")
	public Result returnFromFacebook(HttpServletRequest  request){
		Result res = new Result();
		String code = request.getParameter(CODE);
		if (!StringUtils.isEmpty(code)) {
			String returnUrl = "http://127.0.0.1:9003/other/facebook";
			String access = memberOtherService.getFaceBookAccessToken(code, returnUrl, FACEBOOK_APP_ID, FACEBOOK_APP_SECRET);
			if(StringUtils.isEmpty(access)){
				res.setRet(0);
				res.setErrCode("-20001");
				res.setErrMsg("Handshake failure ");
				return res;
			}
			int start = access.indexOf("access_token=");
			int end = access.indexOf("&expires=");
			String token = access.substring(start, end);
			FaceBookUser fbu = memberOtherService.getFaceBookUser(token);
			if(fbu == null){
				res.setRet(0);
				res.setErrCode("-20002");
				res.setErrMsg("get facebook user failure ");
				return res;
			}
			//把用户存进loginOrRegistration
			memberOtherService.loginOrRegistration(fbu.getEmail(), FACEBOOK_TYPE, fbu.getId(), 1, null);
			res.setRet(CommonUtils.SUCCESS_RES);
			res.setData(fbu);
		}else{
			res.setRet(0);
			res.setErrCode("-20003");
			res.setErrMsg("returnFromFacebook error");
		}
		return res;
	}
	
	/**
	 * Google回调
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/google")
	public Result returnFromGoogle(HttpServletRequest  request){
		Result res = new Result();
		String code = request.getParameter(CODE);
		if (!StringUtils.isEmpty(code)) {
			String returnUrl = "http://127.0.0.1:9003/other/google";
			String token = memberOtherService.getGoogleAccessToken(code, returnUrl, GOOGLE_APP_ID, GOOGLE_APP_SECRET);
			if(StringUtils.isEmpty(token)){
				res.setRet(0);
				res.setErrCode("-20004");
				res.setErrMsg("Handshake failure ");
				return res;
			}
			GoogleUser gu = memberOtherService.getGoogleUser(token);
			if(gu == null){
				res.setRet(0);
				res.setErrCode("-20005");
				res.setErrMsg("get facebook user failure ");
				return res;
			}
			//把用户存进loginOrRegistration
			String email = gu.getEmails().get(0).getValue();
			String id = gu.getId();
			memberOtherService.loginOrRegistration(email, GOOGLE_TYPE, id, 1, null);
			res.setRet(CommonUtils.SUCCESS_RES);
			res.setData(gu);
		}else{
			res.setRet(0);
			res.setErrCode("-20006");
			res.setErrMsg("returnFromGoogle error");
		}
		
		return res;
	}
	
	/**
	 * Paypal回调
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/paypal")
	public Result returnFromPaypal(HttpServletRequest  request){        
		Result res = new Result();
		String code = request.getParameter(CODE);
		if (!StringUtils.isEmpty(code)) {
			String returnUrl = "http://127.0.0.1:9003/other/paypal";
			String token = memberOtherService.getPaypalAccessToken(code, returnUrl, PAYPAL_APP_ID, PAYPAL_APP_SECRET);
			if(StringUtils.isEmpty(token)){
				res.setRet(0);
				res.setErrCode("-20007");
				res.setErrMsg("Handshake failure ");
				return res;
			}
			PaypalUser pu = memberOtherService.getPaypalUser(token);
			if(pu == null){
				res.setRet(0);
				res.setErrCode("-20008");
				res.setErrMsg("get paypal user failure");
				return res;
			}
			//把用户存进loginOrRegistration{TODO}
			String email = pu.getEmail();
			String id = pu.getUserId();
			memberOtherService.loginOrRegistration(email, PAYPAL_TYPE, id, 1, null);
			res.setRet(CommonUtils.SUCCESS_RES);
			res.setData(pu);
			
		}else{
			res.setRet(0);
			res.setErrCode("-20009");
			res.setErrMsg("returnFromPaypal error");
			return res;
		}
		return res;
	}
}

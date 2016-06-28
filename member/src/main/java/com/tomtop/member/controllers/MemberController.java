package com.tomtop.member.controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tomtop.framework.core.utils.Result;
import com.tomtop.member.models.bo.LoginMemberBo;
import com.tomtop.member.models.bo.RegisterMemberBo;
import com.tomtop.member.models.bo.TokenUUidBo;
import com.tomtop.member.models.filter.LoginMemberFilter;
import com.tomtop.member.models.filter.RegisterMemberFilter;
import com.tomtop.member.service.IMemberService;
import com.tomtop.member.utils.CommonUtils;

/**
 * 用户操作类
 * 
 * @author renyy
 *
 */
@RestController
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	IMemberService memberService;

	/**
	 * 用户登录验证
	 * @param LoginMemberFilter
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/v1/login")
	public Result checkLogin(@RequestBody LoginMemberFilter lfilter){
		Result res = new Result();
		String email = lfilter.getEmail();
		String pwd = lfilter.getPwd();
		Integer websiteid = lfilter.getClient();
		LoginMemberBo lmb = memberService.memberLogin(email, pwd, websiteid);
		Integer re = lmb.getRes();
		if(re == CommonUtils.SUCCESS_RES){
			TokenUUidBo tub = memberService.getToken(email, pwd, websiteid);
			res.setRet(CommonUtils.SUCCESS_RES);
			lmb.setRes(null);
			lmb.setToken(tub.getToken());
			lmb.setUuid(tub.getUuid());
			res.setData(lmb);
		}else{
			String msg = lmb.getMsg();
			res.setRet(CommonUtils.ERROR_RES);
			res.setErrCode(re.toString());
			res.setErrMsg(msg);
		}
		return res;
	}
	
	/**
	 * 用户注册
	 * @param RegisterMemberFilter
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT,value = "/v1/register")
	public Result registeredMember(HttpServletRequest request,
			@RequestBody RegisterMemberFilter rgmfilter){
		Result res = new Result();
		String email = rgmfilter.getEmail();
		String pwd = rgmfilter.getPwd();
		String country = rgmfilter.getCountryShort();
		Integer siteId = rgmfilter.getClient();
		String vhost = request.getRemoteHost();
		
		RegisterMemberBo rmb = memberService.memberRegister(email, pwd, country, siteId, vhost);
		Integer re = rmb.getRes();
		String msg = rmb.getMsg();
		if(re == CommonUtils.SUCCESS_RES){
			res.setRet(CommonUtils.SUCCESS_RES);
		}else{
			res.setRet(CommonUtils.ERROR_RES);
			res.setErrCode(re.toString());
			res.setErrMsg(msg);
		}
		return res;
	}
}

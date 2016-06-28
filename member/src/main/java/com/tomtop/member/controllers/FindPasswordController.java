package com.tomtop.member.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tomtop.framework.core.utils.Result;
import com.tomtop.member.models.bo.BaseBo;
import com.tomtop.member.models.bo.ForgetPasswordBo;
import com.tomtop.member.models.filter.FindPasswordFilter;
import com.tomtop.member.service.IForgotPasswordService;
import com.tomtop.member.utils.CommonUtils;

/**
 * 找回密码操作类
 * 
 * @author renyy
 *
 */
@RestController
@RequestMapping(value = "/findpwd")
public class FindPasswordController {

	@Autowired
	IForgotPasswordService forgotPasswordService;

	/**
	 * 找回密码发送邮件
	 * @param FindPasswordFilter
	 * 			email - client
	 *  
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value="/v1/send")
	public Result findPassword(@RequestBody FindPasswordFilter fpilter){
		Result res = new Result();
		String email = fpilter.getEmail();
		Integer siteid = fpilter.getClient();
		ForgetPasswordBo fpb =  forgotPasswordService.forgotPassword(email, siteid);
		Integer re = fpb.getRes();
		String msg = fpb.getMsg();
		if(re == CommonUtils.SUCCESS_RES){
			res.setRet(CommonUtils.SUCCESS_RES);
			res.setData(fpb.getCid());
		}else{
			res.setRet(CommonUtils.ERROR_RES);
			res.setErrCode(re.toString());
			res.setErrMsg(msg);
		}
		return res;
	}
	
	/**
	 * 通过cid修改密码
	 * @param FindPasswordFilter
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value="/v1/alert")
	public Result alertPassword(@RequestBody FindPasswordFilter fpilter){
		Result res = new Result();
		String cid = fpilter.getCid();
		String pwd = fpilter.getPwd();
		Integer siteid = fpilter.getClient();
		BaseBo bb = forgotPasswordService.alertPassword(cid, pwd, siteid);
		Integer re = bb.getRes();
		String msg = bb.getMsg();
		if(re == CommonUtils.SUCCESS_RES){
			res.setRet(CommonUtils.SUCCESS_RES);
		}else{
			res.setRet(CommonUtils.ERROR_RES);
			res.setErrCode(re.toString());
			res.setErrMsg(msg);
			return res;
		}
		return res;
	}
}

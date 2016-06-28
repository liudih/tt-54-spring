package com.tomtop.member.service;

import com.tomtop.member.models.bo.BaseBo;
import com.tomtop.member.models.bo.ForgetPasswordBo;

public interface IForgotPasswordService {

	public ForgetPasswordBo forgotPassword(String email,Integer siteId);

	public BaseBo alertPassword(String cid,String pwd,Integer siteId);
}

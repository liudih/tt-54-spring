package com.tomtop.member.service;

import com.tomtop.member.models.bo.LoginMemberBo;
import com.tomtop.member.models.bo.RegisterMemberBo;
import com.tomtop.member.models.bo.TokenUUidBo;

public interface IMemberService {

	public LoginMemberBo memberLogin(String email,String pwd,Integer siteId);
	
	public RegisterMemberBo memberRegister(String email,String pwd,String country,
			Integer siteId,String vhost);
	
	public TokenUUidBo getToken(String email, String password, Integer siteId);
}

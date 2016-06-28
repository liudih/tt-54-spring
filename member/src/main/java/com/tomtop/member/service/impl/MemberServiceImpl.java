package com.tomtop.member.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.member.mappers.MemberBaseMapper;
import com.tomtop.member.mappers.MemberEmailVerifyMapper;
import com.tomtop.member.models.bo.LoginMemberBo;
import com.tomtop.member.models.bo.RegisterMemberBo;
import com.tomtop.member.models.bo.TokenUUidBo;
import com.tomtop.member.models.dto.MemberBase;
import com.tomtop.member.models.dto.MemberEmailVerify;
import com.tomtop.member.service.IMemberService;
import com.tomtop.member.utils.CommonUtils;
import com.tomtop.member.utils.CryptoUtils;
import com.tomtop.member.utils.UUIDGenerator;

/**
 * 用户端业务逻辑类
 * @author renyy
 *
 */
@Service
public class MemberServiceImpl implements IMemberService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MemberBaseMapper memberBaseMapper;
	
	@Autowired
	MemberEmailVerifyMapper memberEmailVerifyMapper;
	
	private final int MAIL_VERIFY_TIME = 3;
	
	private final String LOGIN_SECURE = "123";
	
	private static final SimpleDateFormat dateFormater = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * 账号密码验证
	 * @param email
	 * 		                邮箱(必填)
	 * @param pwd
	 * 		               密码(必填)
	 * @param siteId
	 * 		               站点ID(必填)
	 * @return LoginMemberBo
	 * 
	 * @author renyy
	 */
	@Override
	public LoginMemberBo memberLogin(String email,String pwd, Integer siteId) {
		LoginMemberBo lmbo = new LoginMemberBo();
		if(email == null){
			lmbo.setRes(-1001);
			lmbo.setMsg("email is null");
			return lmbo;
		}
		if(siteId == null){
			lmbo.setRes(-1002);
			lmbo.setMsg("siteId is null");
			return lmbo;
		}
		List<MemberBase> mbList = memberBaseMapper.getMemberBase(email, siteId, null, null, null);
		if (mbList != null && mbList.size() > 0) {
			MemberBase mb = mbList.get(0);
			String securityStr = mb.getCpasswd();
			boolean b = CryptoUtils.validateHash(pwd, securityStr);
			if(b){
				lmbo.setRes(CommonUtils.SUCCESS_RES);
				lmbo.setId(mb.getIid());
				lmbo.setEmail(mb.getCemail());
				lmbo.setAccount(mb.getCaccount());
				lmbo.setCountry(mb.getCcountry());
			}else{
				lmbo.setRes(-1004);
				lmbo.setMsg("password is incorrect ");
			}
		}else{
			lmbo.setRes(-1005);
			lmbo.setMsg("not find member");
		}
		return lmbo;
	}

	/**
	 * 注册用户
	 * @param email
	 * 		                邮箱(必填)
	 * @param pwd
	 * 		               密码(必填)
	 * @param country
	 * 		               国家(必填)
	 * @param siteId
	 * 		               站点ID(必填)
	 * @param vhost
	 * 		              来源(必填)
	 * @return RegisterMemberBo
	 * 
	 * @author renyy
	 */
	@Override
	public RegisterMemberBo memberRegister(String email, String pwd,
			String country,Integer siteId,String vhost) {
		RegisterMemberBo rmb = new RegisterMemberBo();
		if(email == null || "".equals(email)){
			rmb.setRes(-1006);
			rmb.setMsg("register email is null");
			return rmb;
		}
		if(siteId == null){
			rmb.setRes(-1007);
			rmb.setMsg("register siteId is null");
			return rmb;
		}
		if(pwd == null || "".equals(pwd)){
			rmb.setRes(-1008);
			rmb.setMsg("register password is null");
			return rmb;
		}
		if(country == null || "".equals(country)){
			rmb.setRes(-1009);
			rmb.setMsg("register country is null");
			return rmb;
		}
		if(!CommonUtils.checkEmail(email)){
			rmb.setRes(-1010);
			rmb.setMsg("register E-mail format is incorrect");
			return rmb;
		}
		List<MemberBase> mbList = memberBaseMapper.getMemberBase(email, siteId, null, null, null);
		if (mbList != null && mbList.size() > 0) {
			rmb.setRes(-1011);
			rmb.setMsg("email already exists.");
			return rmb;
		}
		MemberBase member = new MemberBase();
		member.setCemail(email);
		member.setCpasswd(CryptoUtils.getHash(pwd, 2));
		member.setIgroupid(1); // default 1
		member.setCcountry(country);
		member.setBnewsletter(false);
		member.setBactivated(false);
		member.setCvhost(vhost);
		member.setIwebsiteid(siteId);
		member.setCfirstname("");
		member.setClastname("");
		int ist = memberBaseMapper.insertMemberBase(member);
		if(ist <= 0 ){
			rmb.setRes(-1012);
			rmb.setMsg("registration failed");
			return rmb;
		}else{
			MemberEmailVerify mev = new MemberEmailVerify();
			mev.setCemail(email);
			mev.setIdaynumber(MAIL_VERIFY_TIME);
			ist = memberEmailVerifyMapper.insert(mev);
			if(ist <= 0 ){
				logger.error("MemberServiceImpl registration email verify error [-1013]");
			}
			rmb.setRes(CommonUtils.SUCCESS_RES);
			rmb.setMsg(CommonUtils.SUCCESS_MSG_RES);
		}
		return rmb;
	}
	/**
	 * 当用户登录时来验证账号密码,如果验证通过则会返回一个token和uuid,然后保存token和uuid到cookie来标示用户已经登录
	 * 
	 * @param email
	 * 		                邮箱(必填)
	 * @param pwd
	 * 		               密码(必填)
	 * @param siteId
	 * 		               站点ID(必填)
	 * 
	 * @return TokenUUidBo 验证通过
	 */
	@Override
	public TokenUUidBo getToken(String email, String password, Integer siteId) {
		TokenUUidBo tub = new TokenUUidBo();
		if (email == null || email.length() == 0) {
			tub.setRes(-1101);
			tub.setMsg("getToken email is null");
			return tub;
		}
		if (password == null || password.length() == 0) {
			tub.setRes(-1102);
			tub.setMsg("getToken password is null");
			return tub;
		}
		if (siteId == null ) {
			siteId = 1;
		}
		
		MemberBase member = new MemberBase();
		member.setCemail(email);
		member.setIwebsiteid(siteId);;
		member = memberBaseMapper.getMemberBaseWhere(member);
		boolean b = CryptoUtils.validateHash(password, member.getCpasswd());
		if(!b){
			tub.setRes(-1103);
			tub.setMsg("getToken password is incorrect");
			return tub;
		}
		// 取日期
		Date date = new Date();
		String dateStr = dateFormater.format(date);

		StringBuilder key = new StringBuilder();
		key.append(member.getCemail());
		key.append(member.getCpasswd());
		key.append(LOGIN_SECURE);
		key.append(dateStr);
		String token = CryptoUtils.md5(key.toString());
	
		Integer id = member.getIid();
		String uuid = member.getCuuid();
		if (uuid == null) {
			uuid = UUIDGenerator.createAsString();
			MemberBase udmb = new MemberBase();
			udmb.setIid(id);
			udmb.setCuuid(uuid);
			memberBaseMapper.update(udmb);
		}
		tub.setToken(token);
		tub.setUuid(uuid);
		
		return tub;
	}
}

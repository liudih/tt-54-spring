package com.tomtop.member.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.framework.core.utils.SecurityMD5;
import com.tomtop.member.mappers.ForgetPasswdBaseMapper;
import com.tomtop.member.mappers.MemberBaseMapper;
import com.tomtop.member.models.bo.BaseBo;
import com.tomtop.member.models.bo.ForgetPasswordBo;
import com.tomtop.member.models.dto.ForgetPasswdBase;
import com.tomtop.member.models.dto.MemberBase;
import com.tomtop.member.service.IForgotPasswordService;
import com.tomtop.member.utils.CommonUtils;
import com.tomtop.member.utils.CryptoUtils;

/**
 * 用户忘记密码业务逻辑类
 * 
 * @author renyy
 */
@Service
public class ForgotPasswordServiceImpl implements IForgotPasswordService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ForgetPasswdBaseMapper forgetPassMapper;
	
	@Autowired
	MemberBaseMapper memberBaseMapper;
	
	private final int MAIL_VERIFY_TIME = 3;//先手动设置值.后续在parameter中获取
	
	/**
	 * 用户忘记密码发送验证邮件
	 * @param email
	 * 		                邮箱(必填)
	 * @param siteId
	 * 		               站点ID(必填)
	 * 
	 * @return BaseBo
	 * 
	 * @author renyy
	 */
	@Override
	public ForgetPasswordBo forgotPassword(String email, Integer siteId) {
		ForgetPasswordBo bb = new ForgetPasswordBo();
		if(email == null){
			bb.setRes(-1201);
			bb.setMsg("email is null");
			return bb;
		}
		if(siteId == null){
			bb.setRes(-1202);
			bb.setMsg("siteId is null");
			return bb;
		}
		Integer nums = forgetPassMapper.getCountByCmembermailAndDcreatedate(email, siteId);
		if (nums >= MAIL_VERIFY_TIME) {
			bb.setRes(-1203);
			bb.setMsg("More than a daily verification visits (3)");
			return bb;
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		long date = now.getTime();// 忽略毫秒数
		String sid = date + uuid;
		String cid = SecurityMD5.encode(sid);// 数字签名
		String code = CryptoUtils.getRandomString(6);
		cleanForgetPasswor(nums, email, now);
		ForgetPasswdBase fpb = new ForgetPasswdBase();
		fpb.setCid(cid);
		fpb.setCmemberemail(email);
		fpb.setCrandomcode(code);
		fpb.setBuse(true);
		fpb.setIwebsiteid(siteId);
		//添加记录到用户找回密码表
		Integer res = addForgetPasswordBass(fpb);
		if(res == 1){
			logger.info("add forget success {} ", email);
		}else{
			logger.error("add forget error {} ", email);
		}
		//获取拼接url or 发送内容
		//String url = "http://127.0.0.1:9001/findpass?cid=" ;
		//[TODO]发送邮件
		bb.setRes(CommonUtils.SUCCESS_RES);
		bb.setMsg(CommonUtils.SUCCESS_MSG_RES);
		bb.setCid(cid);
		return bb;
	}
	
	/**
	 * 添加记录到 用户找回密码表 (私有)
	 * 
	 * @param ForgetPasswdBase
	 * 
	 * @return Integer
	 */
	private Integer addForgetPasswordBass(ForgetPasswdBase fpb){
		return forgetPassMapper.insert(fpb);
	}
	
	/**
	 * 用户再一次发送忘记密码时把上一次的token清除(私有)
	 * 
	 * @param ForgetPasswdBase
	 * 
	 */
	private void cleanForgetPasswor(Integer nums,String email,Timestamp now){
		if(nums > 0){
			ForgetPasswdBase updUse = new ForgetPasswdBase();
			updUse.setBuse(false);
			updUse.setCmemberemail(email);
			updUse.setDcreatedate(now);
			forgetPassMapper.update(updUse);
		}
	}

	/**
	 * 忘记密码-修改
	 * @param cid
	 * 		               唯一id(必填)
	 * @param pwd
	 *  			密码(必填)
	 * @param siteId
	 * 		               站点ID(必填)
	 * 
	 * @return BaseBo
	 * 
	 * @author renyy
	 */
	@Override
	public BaseBo alertPassword(String cid, String pwd,
			Integer siteId) {
		BaseBo bb = new BaseBo();
		if(cid == null){
			bb.setRes(-1205);
			bb.setMsg("cid is null");
			return bb;
		}
		if(siteId == null){
			bb.setRes(-1206);
			bb.setMsg("siteId is null");
			return bb;
		}
		if(pwd == null){
			bb.setRes(-1207);
			bb.setMsg("password is null");
			return bb;
		}
		ForgetPasswdBase fpb = forgetPassMapper.selectByCid(cid);
		if(fpb == null){
			bb.setRes(-1208);
			bb.setMsg("this token is fail,ignore change password");
			return bb;
		}
		boolean b = fpb.isBuse();
		if(b){
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			Timestamp createDate = fpb.getDcreatedate();
			Date nowDate = ts;
			Date validate = createDate;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(validate);
			calendar.add(Calendar.DATE, MAIL_VERIFY_TIME);
			validate = calendar.getTime();
			if (nowDate.getTime() > validate.getTime()) {
				logger.error("token has expired ");
				bb.setRes(-1209);
				bb.setMsg("token has expired");
				return bb;
			}
			String email = fpb.getCmemberemail();
			String cpassword = CryptoUtils.getHash(pwd, 2);
			int res = -1;
			MemberBase mb = new MemberBase();
			mb.setCemail(email);
			mb.setCpasswd(cpassword);
			mb.setIwebsiteid(siteId);
			res = memberBaseMapper.update(mb);
			if(res <= 0){
				logger.error("change password error ");
				bb.setRes(-1210);
				bb.setMsg("change password error");
				return bb;
			}
			ForgetPasswdBase updfpb = new ForgetPasswdBase();
			updfpb.setBuse(false);
			updfpb.setCid(cid);
			res = forgetPassMapper.update(updfpb);
			if(res <= 0){
				logger.error("cid update bus false error {}", cid);
			}
			bb.setRes(CommonUtils.SUCCESS_RES);
			bb.setMsg(CommonUtils.SUCCESS_MSG_RES);
		}else{
			bb.setRes(-1211);
			bb.setMsg("this token is fail,ignore change password");
		}
		return bb;
	}
}

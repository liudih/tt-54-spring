package com.tomtop.management.authority.services.serviceImp;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.tomtop.management.authority.mapper.impl.UserRoleRepositoryImpl;
import com.tomtop.management.authority.services.iservice.IUserService;
import com.tomtop.management.common.MD5;
import com.tomtop.management.ebean.authority.model.AdminUser;

@Service
public class CheckLoginUser {

	private static Logger log = Logger
			.getLogger(CheckLoginUser.class.getName());
	@Autowired
	UserRoleRepositoryImpl userRoleRepositoryImpl;
	@Autowired
	IUserService userService;

	@Autowired
	CurrentUserService currentUserService;

	@Value("${login.encode.key}")
	private String encodeKey;

	@Value("${manager.host}")
	private String managerHost;
	@Value("${managerment.sysName}")
	private String sysName;

	@Value("${login.time.limit}")
	private String timeLimit;

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getManagerHost() {
		return managerHost;
	}

	public void setManagerHost(String managerHost) {
		this.managerHost = managerHost;
	}

	public String getEncodeKey() {
		return encodeKey;
	}

	public void setEncodeKey(String encodeKey) {
		this.encodeKey = encodeKey;
	}

	public String getSign(String jobNumber, String timestamp) {
		String sessionId = currentUserService.getUserSessionId(jobNumber);
		// 签名验证
		String temp = encodeKey + jobNumber + timestamp + sysName + sessionId;
		return MD5.md5(temp);

	}

	public Map<String, Object> validateLoginUser(String jobNumber,
			String sysName, String timestamp, String signData,
			AdminUser adminUser) {
		Map<String, Object> result = Maps.newHashMap();
		if (StringUtils.isNotEmpty(timestamp)) {

			log.info("timestamp:" + timestamp);
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ssZ");
			try {
				Date dt = sdf.parse(timestamp);
				String newStr = sdf.format(new Date());
				log.info("newStr:   " + newStr);

				Date newDt = sdf.parse(newStr);
				Long diffTm = (newDt.getTime() - dt.getTime()) % 1000 != 0 ? ((newDt
						.getTime() - dt.getTime()) / 1000 + 1) : (newDt
						.getTime() - dt.getTime()) / 1000;//相隔多少秒
				Long limit = new Long(timeLimit);
				if (diffTm > limit) {
					log.info("validateLoginUser diffTm:" + diffTm + "  limit:"
							+ limit);
					result.put("timestamp", "time out");
				} else {
					log.debug("validateLoginUser diffTm ok");
				}

			} catch (ParseException e) {
				result.put("timestamp", e);
				log.error("validateLoginUser error", e);
			}
		}
		validateParams(jobNumber, "jobNumber", result);
		validateParams(sysName, "sysName", result);
		// validateParams(times,"timestamp",result);//时间戳校验
		validateParams(signData, "signData", result);
		// 签名验证
		String temp = encodeKey + jobNumber + timestamp + sysName;
		String md5 = MD5.md5(temp);
		if (StringUtils.isNotEmpty(signData) && !signData.equals(md5)) {
			log.info("validateLoginUser sign fail ! md5:" + md5 + "  signData:"
					+ signData);
			result.put("sign", "signData fail");
		}
		// 如果参数通过，则验证用户
		if (MapUtils.isEmpty(result)) {
			Optional<AdminUser> userByEmail = userService
					.getUserByJobnumber(jobNumber);
			if (userByEmail == null || !userByEmail.isPresent()) {// 如果用户不存在
				log.debug("validateLoginUser jobNumber  not exist");
				result.put("jobNumber", "jobNumber not exist");
			} else {
				AdminUser adminUser2 = userByEmail.get();
				try {
					BeanUtils.copyProperties(adminUser, adminUser2);
				} catch (IllegalAccessException e) {
					result.put("user", "An error occurred ");
					log.error("validateLoginUser copy adminuser error", e);
				} catch (InvocationTargetException e) {
					result.put("user", "An error occurred ");
					log.error("validateLoginUser copy adminuser error", e);
				}
			}
		}

		// 用户通过则验证签名
		return result;
	}

	private void validateParams(Object obj, String paramName,
			Map<String, Object> map) {
		if (map == null || StringUtils.isEmpty(paramName)) {
			log.info("CheckLoginUser validateParams map or paramName null paramName:"
					+ paramName + "  map:" + map);
			return;
		}
		if (obj instanceof String) {
			if (StringUtils.isEmpty((String) obj)) {
				log.debug("validateParams obj :" + obj + "   paramName:"
						+ paramName);
				map.put(paramName, "param empty");
			}

		}
	}

}

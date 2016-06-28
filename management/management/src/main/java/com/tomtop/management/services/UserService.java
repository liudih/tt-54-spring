package com.tomtop.management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.OptimisticLockException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.MD5;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.ebean.authority.model.UserRoleMap;
import com.tomtop.management.ebean.authority.model.UserSiteMap;
import com.tomtop.management.service.model.UserObject;

@Service
public class UserService {

	/**
	 * 
	 * @Title: getUserPage
	 * @Description: TODO(分页查询用户)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param user
	 * @param @param role
	 * @param @return    
	 * @return Page<UserObject>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public Page<UserObject> getUserPage(int pageNo, int pageLimit, AdminUser user, Long role) {
		Page<UserObject> userPage = new Page<UserObject>();
		String q = "find AdminUser where 1=1 ";
		if (StringUtil.isNotEmpty(user.getUserName())) {
			q += " and userName like '%" + user.getUserName() + "%'";
		}
		if (StringUtil.isNotEmpty(user.getJobNumber())) {
			q += " and jobNumber like '%" + user.getJobNumber() + "%'";
		}
		if (null != role) {
			List<UserRoleMap> urs = UserRoleMap.db().find(UserRoleMap.class).where().eq("roleId", role).findList();
			List<Long> roleIds = Lists.transform(urs, ur -> ur.getUserId());
			String rIds = "";
			for (int i = 0; i < roleIds.size(); i++) {
				if (i == 0) {
					rIds += roleIds.get(i);
				} else {
					rIds += "," + roleIds.get(i);
				}
			}
			q += " and id in (" + rIds + ")";
		}
		PagedList<AdminUser> userPageList = AdminUser.db().createQuery(AdminUser.class, q).orderBy()
				.desc("whenModified").findPagedList(pageNo - 1, pageLimit);
		userPageList.loadRowCount();
		userPage.setCount(userPageList.getTotalRowCount());
		userPage.setLimit(pageLimit);
		userPage.setPageNo(pageNo);
		List<UserObject> uoList = new ArrayList<UserObject>();
		for (AdminUser adminUser : userPageList.getList()) {
			UserObject uo = new UserObject();
			try {
				BeanUtils.copyProperties(uo, adminUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
			uoList.add(uo);
		}
		for (UserObject uo : uoList) {
			uo.setCrateTime(DateUtil.dateFormat(uo.getWhenCreated()));
			uo.setUpdateTime(DateUtil.dateFormat(uo.getWhenModified()));
		}
		userPage.setList(uoList);
		return userPage;
	}

	/**
	 * 
	 * @Title: addUser
	 * @Description: TODO(新增用户)
	 * @param @param u
	 * @param @param roleIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public int addUser(AdminUser u, String roleIds) {
		int addCount = 0;
		u.setStatus("1");
		u.setPassword(MD5.md5(u.getPassword()));
		AdminRole role = null;
		if (StringUtil.isNotEmpty(roleIds)) {
			if (roleIds.indexOf(",") != -1) {
				String[] ids = roleIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					role = AdminRole.db().find(AdminRole.class, ids[i]);
					if ("ADMIN".equals(role.getRoleName())) {
						u.setAdmin(true);
						break;
					}
				}
			} else {
				role = AdminRole.db().find(AdminRole.class, Long.parseLong(roleIds));
				if ("ADMIN".equals(role.getRoleName())) {
					u.setAdmin(true);
				}
			}
		}
		try {
			AdminUser.db().save(u);
			addCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: getUserById
	 * @Description: TODO(通过id查询用户)
	 * @param @param id
	 * @param @return    
	 * @return AdminUser    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public AdminUser getUserById(Integer id) {
		return AdminUser.db().find(AdminUser.class, id);
	}

	/**
	 * 
	 * @Title: updateUser
	 * @Description: TODO(修改用户)
	 * @param @param u
	 * @param @param roleIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public int updateUser(AdminUser u, String roleIds) {
		int updateCount = 0;
		AdminUser user = AdminUser.db().find(AdminUser.class, u.getId());
		user.setEmail(u.getEmail());
		user.setJobNumber(u.getJobNumber());
		user.setPhone(u.getPhone());
		user.setUserName(u.getUserName());
		AdminRole role = null;
		if (StringUtil.isNotEmpty(roleIds)) {
			if (roleIds.indexOf(",") != -1) {
				String[] ids = roleIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					role = AdminRole.db().find(AdminRole.class, ids[i]);
					if ("ADMIN".equals(role.getRoleName())) {
						user.setAdmin(true);
						break;
					}
				}
			} else {
				role = AdminRole.db().find(AdminRole.class, Long.parseLong(roleIds));
				if ("ADMIN".equals(role.getRoleName())) {
					user.setAdmin(true);
				}
			}
		}
		try {
			AdminUser.db().save(user);
			updateCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	/**
	 * 
	 * @Title: userNameUniqueValidate
	 * @Description: TODO(用户名唯一校验)
	 * @param @param id
	 * @param @param userName
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public int userNameUniqueValidate(Integer id, String userName) {
		int findCount = 0;
		if (id == 0) {
			findCount = AdminUser.db().find(AdminUser.class).where().eq("userName", userName).findRowCount();
		} else {
			AdminUser u = AdminUser.db().find(AdminUser.class, id);
			if (!u.getUserName().equals(userName)) {
				findCount = AdminUser.db().find(AdminUser.class).where().eq("userName", userName).findRowCount();
			}
		}
		return findCount;
	}

	/**
	 * 
	 * @Title: deleteUser
	 * @Description: TODO(删除用户)
	 * @param @param uids
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public int deleteUser(String uids) {
		AdminUser u = null;
		int deleteCount = 0;
		if (uids.indexOf(",") != -1) {
			String[] ids = uids.split(",");
			for (int i = 0; i < ids.length; i++) {
				u = AdminUser.db().find(AdminUser.class, Integer.parseInt(ids[i]));
				try {
					AdminUser.db().delete(u);
					deleteURAndUS(u);
					deleteCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			u = AdminUser.db().find(AdminUser.class, Integer.parseInt(uids));
			try {
				AdminUser.db().delete(u);
				deleteURAndUS(u);
				deleteCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCount;
	}

	/**
	 * 
	 * @Title: addRoleAndSite
	 * @Description: TODO(新增用户角色映射和用户站点映射)
	 * @param @param userName
	 * @param @param roleIds
	 * @param @param siteIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public int addRoleAndSite(String userName, String roleIds, String siteIds) {
		int addCount = 0;
		AdminUser user = getUserByUserName(userName);
		String[] ids = null;
		if (StringUtil.isNotEmpty(roleIds)) {
			if (roleIds.indexOf(",") != -1) {
				ids = roleIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					UserRoleMap urMap = new UserRoleMap();
					urMap.setUserId(user.getId());
					urMap.setRoleId(Long.valueOf(ids[i]));
					try {
						UserRoleMap.db().save(urMap);
						addCount++;
					} catch (OptimisticLockException e) {
						e.printStackTrace();
					}
				}
			} else {
				UserRoleMap urMap = new UserRoleMap();
				urMap.setUserId(user.getId());
				urMap.setRoleId(Long.valueOf(roleIds));
				try {
					UserRoleMap.db().save(urMap);
					addCount++;
				} catch (OptimisticLockException e) {
					e.printStackTrace();
				}
			}
		}
		if (StringUtil.isNotEmpty(siteIds)) {
			if (siteIds.indexOf(",") != -1) {
				ids = siteIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					UserSiteMap usMap = new UserSiteMap();
					usMap.setUser_id(user.getId());
					usMap.setSite_id(Long.valueOf(ids[i]));
					try {
						UserSiteMap.db().save(usMap);
						addCount++;
					} catch (OptimisticLockException e) {
						e.printStackTrace();
					}
				}
			} else {
				UserSiteMap usMap = new UserSiteMap();
				usMap.setUser_id(user.getId());
				usMap.setSite_id(Long.valueOf(siteIds));
				try {
					UserSiteMap.db().save(usMap);
					addCount++;
				} catch (OptimisticLockException e) {
					e.printStackTrace();
				}
			}
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: getUserByUserName
	 * @Description: TODO(通过用户名查询用户)
	 * @param @param userName
	 * @param @return    
	 * @return AdminUser    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public AdminUser getUserByUserName(String userName) {
		return AdminUser.db().find(AdminUser.class).where().eq("userName", userName).findUnique();
	}

	/**
	 * 
	 * @Title: updateRoleAndSite
	 * @Description: TODO(修改用户角色映射和用户站点映射)
	 * @param @param userName
	 * @param @param roleIds
	 * @param @param siteIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public int updateRoleAndSite(String userName, String roleIds, String siteIds) {
		int updateCount = 0;
		AdminUser user = getUserByUserName(userName);
		deleteURAndUS(user);
		updateCount = addRoleAndSite(userName, roleIds, siteIds);
		return updateCount;
	}

	/**
	 * 
	 * @Title: getURListById
	 * @Description: TODO(通过用户Id查询用户角色映射)
	 * @param @param id
	 * @param @return    
	 * @return List<UserRoleMap>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public List<UserRoleMap> getURListById(Long id) {
		return UserRoleMap.db().find(UserRoleMap.class).where().eq("userId", id).findList();
	}

	/**
	 * 
	 * @Title: getUSListById
	 * @Description: TODO(通过用户id查询用户站点映射)
	 * @param @param id
	 * @param @return    
	 * @return List<UserSiteMap>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public List<UserSiteMap> getUSListById(Long id) {
		return UserSiteMap.db().find(UserSiteMap.class).where().eq("user_id", id).findList();
	}

	/**
	 * 
	 * @Title: deleteURAndUS
	 * @Description: TODO(删除用户角色映射和用户站点映射)
	 * @param @param user    
	 * @return void    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	private void deleteURAndUS(AdminUser user) {
		List<UserRoleMap> urMapList = UserRoleMap.db().find(UserRoleMap.class).where().eq("userId", user.getId())
				.findList();
		if (null != urMapList && urMapList.size() > 0) {
			for (UserRoleMap urMap : urMapList) {
				UserRoleMap.db().delete(urMap);
			}
		}
		List<UserSiteMap> usMapList = UserSiteMap.db().find(UserSiteMap.class).where().eq("user_id", user.getId())
				.findList();
		if (null != usMapList && usMapList.size() > 0) {
			for (UserSiteMap usMap : usMapList) {
				UserSiteMap.db().delete(usMap);
			}
		}
	}

	/**
	 * 
	 * @Title: getRandomPassword
	 * @Description: TODO(获取随机密码)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public String getRandomPassword() {
		String[] baseString = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		String randomString = "";
		Random r = new Random();
		for (int i = 0; i < 6; i++) {
			randomString += baseString[r.nextInt(baseString.length)];
		}
		return randomString;
	}

	/**
	 * 
	 * @Title: resetPassword
	 * @Description: TODO(重置密码)
	 * @param @param u
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	public boolean resetPassword(AdminUser u) {
		try {
			AdminUser user = AdminUser.db().find(AdminUser.class, u.getId());
			user.setPassword(MD5.md5(u.getPassword()));
			AdminUser.db().update(user);
		} catch (OptimisticLockException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: jobNumberUniqueValidate
	 * @Description: TODO(工号唯一校验)
	 * @param @param id
	 * @param @param jobNumber
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月20日
	 */
	public int jobNumberUniqueValidate(Integer id, String jobNumber) {
		int findCount = 0;
		if (id == 0) {
			findCount = AdminUser.db().find(AdminUser.class).where().eq("jobNumber", jobNumber).findRowCount();
		} else {
			AdminUser u = AdminUser.db().find(AdminUser.class, id);
			if (!u.getJobNumber().equals(jobNumber)) {
				findCount = AdminUser.db().find(AdminUser.class).where().eq("jobNumber", jobNumber).findRowCount();
			}
		}
		return findCount;
	}
}

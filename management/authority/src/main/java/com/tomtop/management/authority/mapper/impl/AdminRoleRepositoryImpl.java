package com.tomtop.management.authority.mapper.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Lists;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.forms.AdminRoleForm;

@Component
public class AdminRoleRepositoryImpl {

	private static Logger log = Logger.getLogger(AdminRoleRepositoryImpl.class
			.getName());

	public List<AdminRoleForm> queryAdminRole(int pageNo, int pageSize,
			AdminRoleForm ar) throws IllegalAccessException,
			InvocationTargetException {
		List<AdminRoleForm> list = Lists.newArrayList();
		String q = "SELECT id ,role_name  roleName,create_user whoCreated, create_date whenCreated,update_user whoModified,update_date whenModified ,status "
				+ " from admin_role r  where 1=1 ";
		if (ar.getId() != null) {
			q = q + " and id=" + ar.getId() + " ";
		}
		if (StringUtils.isNotEmpty(ar.getRoleName())) {
			q = q + " and role_name='" + ar.getRoleName() + "' ";
		}
		if (StringUtils.isNotEmpty(ar.getWhoCreated())) {
			q = q + " and create_user='" + ar.getWhoCreated() + "' ";
		}
		if (StringUtils.isNotEmpty(ar.getWhoModified())) {
			q = q + " and update_user='" + ar.getWhoModified() + "' ";
		}
		if (StringUtils.isNotEmpty(ar.getStatus())) {
			q = q + " and status='" + ar.getStatus() + "' ";
		}
		q = timeFilterSql("admin_role", q, "create_date",
				ar.getWhenCreatedStart(), ar.getWhenCreatedEnd());
		q = timeFilterSql("admin_role", q, "update_date",
				ar.getWhenModifiedStart(), ar.getWhenModifiedEnd());
		// 限制结果
		int start = (pageNo - 1) * pageSize;
		int end = (pageNo * pageSize);
		q = q + " order by update_date desc limit " + start + "," + end;
		SqlQuery createSqlQuery = AdminRole.db().createSqlQuery(q);
		Set<SqlRow> findSet = createSqlQuery.findSet();
		if (CollectionUtils.isNotEmpty(findSet)) {
			for (SqlRow sqlRow : findSet) {
				AdminRoleForm adminRoleForm = new AdminRoleForm();
				adminRoleForm.setId(sqlRow.getLong("id"));
				adminRoleForm.setRoleName(sqlRow.getString("roleName"));
				adminRoleForm.setStatus(sqlRow.getString("status"));
				adminRoleForm.setWhoCreated(sqlRow.getString("whoCreated"));
				adminRoleForm.setWhoModified(sqlRow.getString("whoModified"));
				Timestamp timestamp = sqlRow.getTimestamp("whenCreated");
				Timestamp timestamp2 = sqlRow.getTimestamp("whenModified");
				if (timestamp != null) {

					adminRoleForm.setWhenCreated(timestamp2.toString());
				}
				if (timestamp2 != null) {

					adminRoleForm.setWhenModified(timestamp2.toString());
				}
				list.add(adminRoleForm);
			}
		}
		return list;
	}

	public int queryAdminRoleSize(AdminRoleForm ar)
			throws IllegalAccessException, InvocationTargetException {
		String q = "SELECT count(1) count from admin_role r  where 1=1 ";
		if (ar.getId() != null) {
			q = q + " and id=" + ar.getId() + " ";
		}
		if (StringUtils.isNotEmpty(ar.getRoleName())) {
			q = q + " and role_name='" + ar.getRoleName() + "' ";
		}
		if (StringUtils.isNotEmpty(ar.getWhoCreated())) {
			q = q + " and create_user='" + ar.getWhoCreated() + "' ";
		}
		if (StringUtils.isNotEmpty(ar.getWhoModified())) {
			q = q + " and update_user='" + ar.getWhoModified() + "' ";
		}
		if (StringUtils.isNotEmpty(ar.getStatus())) {
			q = q + " and status='" + ar.getStatus() + "' ";
		}
		q = timeFilterSql("admin_role", q, "create_date",
				ar.getWhenCreatedStart(), ar.getWhenCreatedEnd());
		q = timeFilterSql("admin_role", q, "update_date",
				ar.getWhenModifiedStart(), ar.getWhenModifiedEnd());
		SqlQuery createSqlQuery = AdminRole.db().createSqlQuery(q);
		Integer count = Integer.parseInt(createSqlQuery.findList().get(0)
				.get("count")
				+ "");

		return count;
	}

	private String timeFilterSql(String table, String sqlStr, String sqlParam,
			Timestamp startTs, Timestamp endTs) {
		if (StringUtils.isEmpty(sqlStr) || StringUtils.isEmpty(sqlParam)
				|| (startTs == null && endTs == null)
				|| (endTs != null && StringUtils.isEmpty(table))) {
			return sqlStr;
		}
		if (startTs != null && endTs != null) {
			sqlStr = sqlStr + " and " + sqlParam + " BETWEEN " + startTs
					+ " and " + endTs + " ";
		} else if (startTs != null) {
			sqlStr = sqlStr + " and " + sqlParam + " BETWEEN " + startTs
					+ " and SYSDATE() ";
		} else if (endTs != null) {
			sqlStr = sqlStr + " and " + sqlParam + " BETWEEN (SELECT min("
					+ sqlParam + ") from " + table + " limit 1) and " + endTs
					+ " ";
		}
		return sqlStr;
	}

	public void addAdminRole(AdminRole ar) {
		try {
			AdminRole.db().save(ar);
		} catch (OptimisticLockException e) {
			log.error("addAdminRole error", e);
			throw e;
		}
	}

	public AdminRole getAdminRoleById(Long id) {
		if (null != id) {
			return AdminRole.db().find(AdminRole.class, id);
		} else {
			return null;
		}
	}

	public void updateAdminRole(AdminRole bc) throws Exception {
		AdminRole adminRole = AdminRole.db().find(AdminRole.class, bc.getId());
		if (adminRole == null) {
			throw new Exception(bc.getId() + " not fond");
		} else {
			if (bc.getRoleName() != null) {
				adminRole.setRoleName(bc.getRoleName());
			}
			if (bc.getStatus() != null) {
				adminRole.setStatus(bc.getStatus());
			}
			if (bc.getWhoCreated() != null) {
				adminRole.setWhoModified(bc.getWhoCreated());
			}

			adminRole.setWhenModified(new Timestamp(new Date().getTime()));
			try {
				AdminRole.db().save(adminRole);
			} catch (OptimisticLockException e) {
				log.error("addAdminRole error", e);
				throw e;
			}
		}
	}

	public void delete(String id) {
		AdminRole.db().delete(AdminRole.class, id);
	}

	public boolean exist(Integer id, String roleName) {
		
		int findCount = 0;
		if (id == 0) {//新增
			findCount = AdminRole.db().find(AdminRole.class).where().eq("role_name", roleName).findRowCount();
		} else {//更新
			AdminRole u = AdminRole.db().find(AdminRole.class, id);
			if(u==null){
				return true;
			}else if (u.getRoleName().equals(roleName)) {
				return false;
			}else{
				return true;
			}
		}
		return findCount==0?false:true;
		
	}

}

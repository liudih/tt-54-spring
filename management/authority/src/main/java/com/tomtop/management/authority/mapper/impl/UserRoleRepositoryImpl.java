package com.tomtop.management.authority.mapper.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
/*import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.domain.Sort;*/
import org.springframework.stereotype.Service;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Lists;
import com.tomtop.management.authority.mapper.IUserRoleRepository;
import com.tomtop.management.ebean.authority.model.AdminRole;

@Service
public class UserRoleRepositoryImpl implements IUserRoleRepository {

	/*private static Logger log = Logger.getLogger(UserRoleRepositoryImpl.class
			.getName());*/

	@Override
	public List<AdminRole> getRolesByUserId(Long userId) {
		List<AdminRole> list = Lists.newArrayList();
		String query = "SELECT R.id,R.role_name FROM admin_role R "
				+ "WHERE 1=1 "
				+ " AND EXISTS(SELECT M.role_id FROM user_manager U,user_role_map M "
				+ "	WHERE U.ID=M.USER_ID " + "	AND M.role_id=R.id and U.id="+userId+")";

		SqlQuery createSqlQuery = AdminRole.db().createSqlQuery(query);
		Set<SqlRow> findSet = createSqlQuery.findSet();
		if (CollectionUtils.isNotEmpty(findSet)) {
			for (SqlRow sqlRow : findSet) {

				AdminRole adminRole = new AdminRole();
				adminRole.setId(sqlRow.getLong("id"));
				adminRole.setRoleName(sqlRow.getString("role_name"));
				list.add(adminRole);

			}
		}
		return list;
	}

	@Override
	public AdminRole getRoleById(int roleId) {
		return AdminRole.db().find(AdminRole.class, roleId);
	}

	@Override
	public AdminRole getRoleByName(String roleName) {
		List<AdminRole> findList = AdminRole.db().find(AdminRole.class).where()
				.eq("role_name", roleName).findList();
		if (CollectionUtils.isNotEmpty(findList)) {
			return findList.get(0);
		}
		return null;
	}

}

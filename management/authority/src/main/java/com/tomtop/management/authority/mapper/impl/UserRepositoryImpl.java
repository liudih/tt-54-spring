package com.tomtop.management.authority.mapper.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
/*import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.domain.Sort;*/
import org.springframework.stereotype.Service;

import com.tomtop.management.authority.mapper.IUserRepository;
import com.tomtop.management.ebean.authority.model.AdminUser;

@Service
public class UserRepositoryImpl implements IUserRepository {

	

	@Override
	public AdminUser findOne(Long arg0) {
		int id = Integer.parseInt(arg0.toString());
		return AdminUser.db().find(AdminUser.class, id);
	}


	@Override
	public Optional<AdminUser> findOneByEmail(String email) {
		List<AdminUser> list = AdminUser.db().find(AdminUser.class).where()
				.eq("email", email).findList();
		if (CollectionUtils.isNotEmpty(list)) {
			return Optional.of(list.get(0));
		}
		return null;
	}

	@Override
	public Optional<AdminUser> findOneByJobnumber(String jobnumber) {
		List<AdminUser> list = AdminUser.db().find(AdminUser.class).where()
				.eq("job_number", jobnumber).findList();
		if (CollectionUtils.isNotEmpty(list)) {
			return Optional.of(list.get(0));
		}
		return null;
	}


	@Override
	public void save(AdminUser user) {
		AdminUser.db().save(user);
	}

}

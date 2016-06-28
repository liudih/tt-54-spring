package com.tomtop.management.authority.services.iservice;

import java.util.Collection;
import java.util.Optional;

import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.forms.UserCreateForm;

public interface IUserService {
	Optional<AdminUser> getUserById(long id);

    Optional<AdminUser> getUserByEmail(String email);

    Collection<AdminUser> getAllUsers();

    String create(UserCreateForm form);

	Optional<AdminUser> getUserByJobnumber(String jobnumber);

}

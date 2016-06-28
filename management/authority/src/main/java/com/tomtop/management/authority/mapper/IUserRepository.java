package com.tomtop.management.authority.mapper;

import java.util.Optional;

import com.tomtop.management.ebean.authority.model.AdminUser;


public interface IUserRepository{
	
	public Optional<AdminUser> findOneByEmail(String email) ;

	Optional<AdminUser> findOneByJobnumber(String jobnumber);

	AdminUser findOne(Long id);

	public void save(AdminUser user);

}


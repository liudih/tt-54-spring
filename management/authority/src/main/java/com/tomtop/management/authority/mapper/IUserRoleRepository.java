package com.tomtop.management.authority.mapper;

import java.util.List;

import com.tomtop.management.ebean.authority.model.AdminRole;


public interface IUserRoleRepository{
	
	public List<AdminRole> getRolesByUserId(Long  userId);
	
	public AdminRole getRoleById(int  roleId);
	
	public AdminRole getRoleByName(String  roleName);

	
}


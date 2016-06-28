package com.tomtop.management.authority.services.serviceImp;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.management.authority.mapper.IUserRepository;
import com.tomtop.management.authority.mapper.impl.UserRoleRepositoryImpl;
import com.tomtop.management.authority.services.iservice.IUserService;
import com.tomtop.management.common.MD5;
import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.forms.UserCreateForm;

@Service
public class UserServiceImpl implements IUserService {

	private final IUserRepository userRepository;

	@Autowired
	public UserServiceImpl(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<AdminUser> getUserById(long id) {
		return Optional.ofNullable(userRepository.findOne(id));
	}

	@Override
	public Optional<AdminUser> getUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}

	@Override
	public Optional<AdminUser> getUserByJobnumber(String jobnumber) {
		return userRepository.findOneByJobnumber(jobnumber);
	}

	@Override
	public String create(UserCreateForm form) {
		AdminUser user = new AdminUser();
		user.setEmail(form.getEmail());
		user.setPassword(MD5.md5(form.getPassword()));
		userRepository.save(user);
		
		return "SUCCESS";
	}

	@Override
	public Collection<AdminUser> getAllUsers() {
		return null;
	}


}

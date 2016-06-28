package com.tomtop.member.mappers;

import com.tomtop.member.models.dto.MemberEmailVerify;


public interface MemberEmailVerifyMapper {

	MemberEmailVerify selectByPrimaryKey(Integer iid);
	
	int deleteByPrimaryKey(Integer iid);
	
	int insert(MemberEmailVerify record);

}

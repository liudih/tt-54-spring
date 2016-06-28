package com.tomtop.management.authority.services.serviceImp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tomtop.management.common.MD5;

public class MD5Encoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return MD5.md5(rawPassword.toString());
		// return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (StringUtils.isEmpty(encodedPassword) || rawPassword == null) {
			return false;
		}
		return encodedPassword.equals(encode(rawPassword));
	}

	public static void main(String[] args) {
		MD5Encoder md5 = new MD5Encoder();
		System.out.print(md5
				.encode("admin123456"));
	}
}

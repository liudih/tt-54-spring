package com.tomtop.management.authority.services.iservice;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface IUserDetails {
	Collection<? extends GrantedAuthority> getAuthorities();
    String getPassword();
    String getUsername();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}

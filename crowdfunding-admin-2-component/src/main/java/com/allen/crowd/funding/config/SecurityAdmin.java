package com.allen.crowd.funding.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.allen.crowd.funding.entity.Admin;

public class SecurityAdmin extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Admin originalAdmin;
	
	public Admin getOriginalAdmin() {
		return originalAdmin;
	}

	public SecurityAdmin(Admin admin, Collection<? extends GrantedAuthority> authorities) {
		super(admin.getUsername(),admin.getUserpswd(), authorities);
		
	}

}

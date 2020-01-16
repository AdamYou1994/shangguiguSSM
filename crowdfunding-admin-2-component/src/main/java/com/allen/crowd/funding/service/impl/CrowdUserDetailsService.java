package com.allen.crowd.funding.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.allen.crowd.funding.config.SecurityAdmin;
import com.allen.crowd.funding.entity.Admin;
import com.allen.crowd.funding.entity.AdminExample;
import com.allen.crowd.funding.entity.Auth;
import com.allen.crowd.funding.entity.AuthExample;
import com.allen.crowd.funding.entity.Role;
import com.allen.crowd.funding.mapper.AdminMapper;
import com.allen.crowd.funding.mapper.AuthMapper;
import com.allen.crowd.funding.mapper.RoleMapper;
import com.allen.crowd.util.MD5Utils;
@Service
public class CrowdUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private AuthMapper authMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminExample adminExample = new AdminExample();
		adminExample.createCriteria().andLoginacctEqualTo(username);
		List<Admin> list = adminMapper.selectByExample(adminExample);
		if (!MD5Utils.collectiveEffective(list)) {
			return null;
		}
		
		Admin admin = list.get(0);
		String userpswd = admin.getUserpswd();
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<Role> roleList = roleMapper.selectAssignedRoleList(admin.getId());
		for (Role role : roleList) {
			String roleName = "ROLE_"+role.getName();
			authorities.add(new SimpleGrantedAuthority(roleName));
		}
		
		AuthExample authExample = new AuthExample();
		authExample.createCriteria().andCategoryIdEqualTo(admin.getId().toString());
		List<Auth> authList = authMapper.selectByExample(authExample);
		for (Auth auth : authList) {
			String authName = auth.getName();
			if (!MD5Utils.stringEffective(authName)) {
				continue;
			}
			authorities.add(new SimpleGrantedAuthority(authName));
		}
		return new SecurityAdmin(admin, authorities);
	}

}

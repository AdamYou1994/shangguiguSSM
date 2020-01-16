package com.allen.crowd.funding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allen.crowd.funding.entity.Auth;
import com.allen.crowd.funding.entity.AuthExample;
import com.allen.crowd.funding.mapper.AuthMapper;
import com.allen.crowd.funding.service.api.AuthService;
@Service
public class AUthServiceImpl implements AuthService {

	@Autowired
	private AuthMapper authMapper;

	@Override
	public List<Auth> getAllAuth() {
		
		return authMapper.selectByExample(new AuthExample());
	}

	@Override
	public List<Integer> getAssignedAuthList(Integer roleId) {
		
		return authMapper.selectAssignedAuthList(roleId);
	}

	@Override
	public void updateRelationShipBetweenAndAuth(Map<String, List<Integer>> assignDataMap) {
		List<Integer> roleIdList = assignDataMap.get("roleIdList");
		Integer roleId = roleIdList.get(0);
		List<Integer> authIdList = assignDataMap.get("authIdList");
		authMapper.deleteOldRelationship(roleId);
		if (authIdList != null && authIdList.size() > 0) {
			authMapper.insertNewRelationshio(roleId,authIdList);
		}
	}
	
}

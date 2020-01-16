package com.allen.crowd.funding.service.api;

import java.util.List;
import java.util.Map;

import com.allen.crowd.funding.entity.Auth;

public interface AuthService {

	List<Auth> getAllAuth();

	List<Integer> getAssignedAuthList(Integer roleId);

	void updateRelationShipBetweenAndAuth(Map<String, List<Integer>> assignDataMap);

}

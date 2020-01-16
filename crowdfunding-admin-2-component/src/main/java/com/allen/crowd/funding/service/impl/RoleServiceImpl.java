package com.allen.crowd.funding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allen.crowd.funding.entity.Role;
import com.allen.crowd.funding.entity.RoleExample;
import com.allen.crowd.funding.mapper.RoleMapper;
import com.allen.crowd.funding.service.api.RoleService;
import com.allen.crowd.util.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword) {
		//开启分页 ，执行查询 ，封装对象
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list = roleMapper.selectForKeywordSearch(keyword);
		return new PageInfo<Role>(list);
	}

	@Override
	public List<Role> getRoleListByIdList(List<Integer> roleIdList) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andIdIn(roleIdList);
		return roleMapper.selectByExample(roleExample);
	}

	@Override
	public void batchRemove(List<Integer> roleIdList) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andIdIn(roleIdList);
		roleMapper.deleteByExample(roleExample);
	}

	@Override
	public void saveRole(String roleName) {
		roleMapper.insert(new Role(null,roleName));
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
		
	}

	@Override
	public List<Role> getAssignedRoleList(Integer adminId) {
		List<Role> roleList = roleMapper.selectAssignedRoleList(adminId);
		return roleList;
	}

	@Override
	public List<Role> getUnAssignedRoleList(Integer adminId) {
		List<Role> roleList = roleMapper.selectUnAssignedRoleList(adminId);
		return roleList;
	}

	@Override
	public void updateRelationship(List<Integer> roleIdList, Integer adminId) {
		roleMapper.deleteOldRelationship(adminId);
		if (MD5Utils.collectiveEffective(roleIdList)) {
			roleMapper.insertNewAdminRelationship(adminId,roleIdList);
		}
	}


}

package com.allen.crowd.funding.handler;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allen.crowd.funding.entity.Auth;
import com.allen.crowd.funding.entity.ResultEntity;
import com.allen.crowd.funding.entity.Role;
import com.allen.crowd.funding.service.api.AuthService;
import com.allen.crowd.funding.service.api.RoleService;

@Controller
public class AssignHandler {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;

	@RequestMapping("/assign/to/assign/role/page")
	public String toAssignRolePage(@RequestParam("adminId") Integer adminId ,Model model) {
		List<Role> assignedRoleList = roleService.getAssignedRoleList(adminId);
		List<Role> unAssignedRoleList = roleService.getUnAssignedRoleList(adminId);
		model.addAttribute("assignedRoleList", assignedRoleList);
		model.addAttribute("unAssignedRoleList", unAssignedRoleList);
		return "assign-role";
	}
	
	@RequestMapping("/assign/role")
	public String toAssignRolePage(@RequestParam(value="roleIdList",required = false) List<Integer> roleIdList,
			@RequestParam("adminId") Integer adminId ,
			@RequestParam("pageNum") String pageNum) {
		roleService.updateRelationship(roleIdList,adminId);
		return "redirect:/admin/query/for/search.html?pageNum="+pageNum;
	}
	
	@ResponseBody
	@RequestMapping("/assign/get/all/auth")
	public ResultEntity<List<Auth>> getAllAuth() {
		List<Auth> authList = authService.getAllAuth();
		return ResultEntity.successWithoutData(authList);
	}
	
	@ResponseBody
	@RequestMapping("/assign/get/assigned/auth/id/list")
	public ResultEntity<List<Integer>> getAssignedAuthList(@RequestParam("roleId") Integer roleId) {
		List<Integer> authList = authService.getAssignedAuthList(roleId);
		return ResultEntity.successWithoutData(authList);
	}
	
	@ResponseBody
	@RequestMapping("/assign/do/assign")
	public ResultEntity<String> doRoleAssignAuth(@RequestBody Map<String,List<Integer>> assignDataMap) {
		authService.updateRelationShipBetweenAndAuth(assignDataMap);
		return ResultEntity.successWithoutData();
	}
}

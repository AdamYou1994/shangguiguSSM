package com.allen.crowd.funding.handler;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allen.crowd.funding.entity.Admin;
import com.allen.crowd.funding.entity.ResultEntity;
import com.allen.crowd.funding.service.api.AdminService;
import com.allen.crowd.util.Constant;
import com.github.pagehelper.PageInfo;

@Controller
public class AdminHandler {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/admin/get/all")
	public String getAll(Model model){
		List<Admin> list = adminService.selectAll();
		model.addAttribute("list",list);
		return "admin-target";
	}
	
	@RequestMapping("/admin/do/login")
	public String doLogin(@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd , 
			Model model , HttpSession session) {
		Admin admin = adminService.login(loginAcct,userPswd);
		if (admin == null) {
			model.addAttribute(Constant.ATTR_NAME_MESSAGE,Constant.MESSAGE_LOGIN_FAILED);
			return "admin-login";
		}
		session.setAttribute(Constant.ATTR_NAME_LOGIN_ADMIN, admin);
		return "redirect:/admin/to/main/page.html";
	}
	
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index.html";
	} 
	
	//如果页面没有指定对应的请求参数，那么可以使用defaultValue指定默认值
	@RequestMapping("/admin/query/for/search")
	public String queryForSearch(
			@RequestParam(value="pageNum",defaultValue = "1")Integer pageNum, 
			@RequestParam(value="pageSize",defaultValue = "5")Integer pageSize ,
			@RequestParam(value="keyword",defaultValue = "")String keyword,
			Model model) {
		PageInfo<Admin> pageInfo = adminService.queryForKeywordSearch(pageNum, pageSize, keyword);
		model.addAttribute(Constant.ATTR_NAME_PAGE_INFO,pageInfo);
		return "admin-page";
	}
	
	@ResponseBody//将当前方法的返回值作为响应体反复会，不经过试图解析器
	@RequestMapping("/admin/batch/remove")
	public ResultEntity batchRemove(@RequestBody List<Integer> adminIdList) {
		try {
			adminService.batchRemove(adminIdList);
			return ResultEntity.successWithoutData();
		}catch (Exception e) {
			return ResultEntity.failed(null, e.getMessage());
		}
	}
	
	@RequestMapping("/admin/save")
	public String saveAdmin(Admin admin) {
		try {
			adminService.saveAdmin(admin);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new RuntimeException(Constant.ATTR_NAME_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
		return "redirect:/admin/query/for/search.html";
	}
	
	// admin/to/edit/page
	@RequestMapping("/admin/to/edit/page")
	public String toEditPage(@RequestParam("adminId") String adminId,Model model) {
		Admin admin = adminService.getAdminById(adminId);
		model.addAttribute("admin",admin);
		return "admin-edit";
	}
	
	@RequestMapping("/admin/update")
	public String updateAdmin(Admin Admin,
			@RequestParam("pageNum")Integer pageNum) {
		try {
			adminService.updateAdmin(Admin);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new RuntimeException(Constant.ATTR_NAME_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
		return "redirect:/admin/query/for/search.html?pageNum="+pageNum;
	}
}

package com.allen.crowd.funding.service.api;

import com.allen.crowd.funding.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @date 2019/12/7 22:38
 */
public interface AdminService {
    List<Admin> selectAll();

    /**
     * 登录的业务处理逻辑
     * @param loginAcct 用户名
     * @param userPswd  密码
     * @return  匹配用户名返回admin对象，不匹配用户名返回null
     */
    Admin login(String loginAcct, String userPswd);
    
    PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize ,String keyword);

	void batchRemove(List<Integer> adminIdList);

	void saveAdmin(Admin admin);

	Admin getAdminById(String adminId);

	void updateAdmin(Admin admin);
}

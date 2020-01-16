package com.allen.crowd.funding.service.impl;

import com.allen.crowd.funding.entity.Admin;
import com.allen.crowd.funding.entity.AdminExample;
import com.allen.crowd.funding.mapper.AdminMapper;
import com.allen.crowd.funding.service.api.AdminService;
import com.allen.crowd.util.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//import com.allen.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @date 2019/12/7 22:38
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public List<Admin> selectAll() {
        return mapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin login(String loginAcct, String userPswd) {
        // 根据loginAcct查询数据库
        AdminExample example = new AdminExample();
        example.createCriteria().andLoginacctEqualTo(loginAcct);
        // 执行查询
       List<Admin> list = mapper.selectByExample(example);
        if (!MD5Utils.collectiveEffective(list)) {
            // 如果查询结果无效返回null
            return null;
        }
        // 获取唯一集合元素
        Admin admin = list.get(0);
        if (null == admin) {
            return null;
        }
        String userPswdDataBase = admin.getUserpswd();
        String userPswdBrowser = passwordEncoder.encode(userPswd);
        //Objects自带的验证相等的方法
        if (!Objects.equals(userPswdDataBase, userPswdBrowser)) {
            return null;
        }
        return admin;
    }

	@Override
	public PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize ,String keyword) {
		//调用pageHelper工具方法开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        //执行分页查询
        List<Admin> admins = mapper.selectAdminListByKeyword(keyword);
        //将结果封装至pageInfo对象中
        return new PageInfo<>(admins);
	}

	@Override
	public void batchRemove(List<Integer> adminIdList) {
		AdminExample adminExample = new AdminExample();
		adminExample.createCriteria().andIdIn(adminIdList);
		mapper.deleteByExample(adminExample);
	}

	@Override
	public void saveAdmin(Admin admin) {
		String pwd = passwordEncoder.encode(admin.getUserpswd());
		admin.setUserpswd(pwd);
		mapper.insert(admin);
	}

	@Override
	public Admin getAdminById(String adminId) {
		Admin admin = mapper.selectByPrimaryKey(Integer.valueOf(adminId));
		return admin;
	}

	@Override
	public void updateAdmin(Admin admin) {
		String pwd = passwordEncoder.encode(admin.getUserpswd());
		admin.setUserpswd(pwd);
		mapper.updateByPrimaryKey(admin);
	}
    
}

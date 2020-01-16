package com.allen.crowd.funding.test;

import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.allen.crowd.funding.entity.Admin;
import com.allen.crowd.funding.entity.Role;
import com.allen.crowd.funding.mapper.AdminMapper;
import com.allen.crowd.funding.mapper.RoleMapper;
import com.allen.crowd.funding.service.api.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class MybatisTest {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Test
	public void testSave() {
		for (int i = 0; i < 100; i++) {
			roleMapper.insert(new Role(null,"role"+i));
		}
	}
	
	@Test
	public void testMybatis() {
		List<Admin> adminList = adminService.selectAll();
		for (Admin admin : adminList) {
			System.out.println(admin);
		}
	}
	
	@Test
	public void testConnection() throws Exception {
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}
	
	@Test
	public void testAdminMapperSearch() {
		String keyword = "a";
		List<Admin> list = adminMapper.selectAdminListByKeyword(keyword);
		for (Admin admin : list) {
			System.out.println(admin);
		}
	}
	
	@Test
	public void batchSaveAdmin() {
		for (int i = 50; i < 500; i++) {
			adminMapper.insert(new Admin(null,"loginAcct"+i,"11111","username"+i,"email"+i+"@qq.com",null));
		}
	}
}


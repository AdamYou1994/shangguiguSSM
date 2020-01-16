package com.allen.crowd.funding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allen.crowd.funding.entity.Menu;
import com.allen.crowd.funding.entity.MenuExample;
import com.allen.crowd.funding.entity.MenuExample.Criteria;
import com.allen.crowd.funding.mapper.MenuMapper;
import com.allen.crowd.funding.service.api.MenuService;
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> getAll() {
		return menuMapper.selectByExample(new MenuExample());
	}

	@Override
	public void saveMenu(Menu menu) {
		menuMapper.insert(menu);
	}

	@Override
	public Menu getMenuById(Integer menuId) {
		MenuExample menuExample = new MenuExample();
		menuExample.createCriteria().andIdEqualTo(menuId);
		List<Menu> list = menuMapper.selectByExample(menuExample);
		return list.get(0) ;
	}

	@Override
	public void updateMenu(Menu menu) {
		
		menuMapper.updateByPrimaryKey(menu);
	}
	
	
}

package com.allen.crowd.funding.service.api;

import java.util.List;

import com.allen.crowd.funding.entity.Menu;

public interface MenuService {

	List<Menu> getAll();

	void saveMenu(Menu menu);

	Menu getMenuById(Integer menuId);

	void updateMenu(Menu menu);

}

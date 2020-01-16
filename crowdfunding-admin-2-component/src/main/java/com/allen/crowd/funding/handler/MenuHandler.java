package com.allen.crowd.funding.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allen.crowd.funding.entity.Menu;
import com.allen.crowd.funding.entity.ResultEntity;
import com.allen.crowd.funding.service.api.MenuService;

@RestController
public class MenuHandler {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/menu/get/whole/treeOld")
	public ResultEntity<Menu> getWholeTreeOld(){
		List<Menu> menuList = menuService.getAll();
		
		Menu rootNode = null;
		
		for (Menu menu : menuList) {
			Integer pid = menu.getPid();
			if (pid == null) {
				rootNode = menu;
				continue;
			}
			
			for (Menu mabyFather : menuList) {
				Integer id = mabyFather.getId();
				if (Objects.equals(pid, id)) {
					mabyFather.getChildren().add(menu);
				}
			}
		}
		return ResultEntity.successWithoutData(rootNode);
	}
	
	@RequestMapping("/menu/get/whole/tree")
	public ResultEntity<Menu> getWholeTree(){
		List<Menu> menuList = menuService.getAll();
		HashMap<Integer, Menu> menuMap = new HashMap<>();
		for (Menu menu : menuList) {
			Integer id = menu.getId();
			menuMap.put(id,menu);
		}
		Menu rootNode = null;
		for (Menu menu : menuList) {
			Integer pid = menu.getPid();
			if (pid == null) {
				rootNode = menu;
				continue;
			}
			Menu father = menuMap.get(pid);
			father.getChildren().add(menu);
		}
		return ResultEntity.successWithoutData(rootNode);
	}
	
	@RequestMapping("/menu/save")
	public ResultEntity<String> saveMenu(Menu menu){
		menuService.saveMenu(menu);
		return ResultEntity.successWithoutData();
	}
	
	@RequestMapping("/menu/update")
	public ResultEntity<String> updateMenu(Menu menu){
		menuService.updateMenu(menu);
		return ResultEntity.successWithoutData();
	}
	
	@RequestMapping("/menu/get/{menuId}")
	public ResultEntity<Menu> getMenuById(@PathVariable("menuId") String menuId){
		int id = Integer.parseInt(menuId);
		Menu menu = menuService.getMenuById(id);
		return ResultEntity.successWithoutData(menu);
	}
}

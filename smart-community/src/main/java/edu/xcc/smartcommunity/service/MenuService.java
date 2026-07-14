package edu.xcc.smartcommunity.service;

import edu.xcc.smartcommunity.entity.pojo.Menu;

import java.util.List;

public interface MenuService {
    
    List<Menu> getMenusByRoleId(Integer roleId);
    
    List<Menu> getAllMenus();
    
    Menu saveMenu(Menu menu);
    
    void deleteMenu(Integer id);
    
    void updateMenu(Menu menu);
    
    Menu getMenuById(Integer id);
}
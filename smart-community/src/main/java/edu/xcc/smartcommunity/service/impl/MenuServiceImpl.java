package edu.xcc.smartcommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.xcc.smartcommunity.entity.pojo.Menu;
import edu.xcc.smartcommunity.mapper.MenuMapper;
import edu.xcc.smartcommunity.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenusByRoleId(Integer roleId) {
        return menuMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<Menu> getAllMenus() {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery(Menu.class);
        wrapper.orderByAsc(Menu::getId);
        return list(wrapper);
    }

    @Override
    public Menu saveMenu(Menu menu) {
        save(menu);
        return menu;
    }

    @Override
    public void deleteMenu(Integer id) {
        removeById(id);
    }

    @Override
    public void updateMenu(Menu menu) {
        updateById(menu);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return getById(id);
    }
}
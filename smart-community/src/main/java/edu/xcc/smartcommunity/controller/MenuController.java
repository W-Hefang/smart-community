package edu.xcc.smartcommunity.controller;

import edu.xcc.smartcommunity.common.Response;
import edu.xcc.smartcommunity.entity.pojo.Menu;
import edu.xcc.smartcommunity.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/getMenusByRoleId")
    public Response<List<Menu>> getMenusByRoleId(@RequestParam Integer roleId) {
        List<Menu> menus = menuService.getMenusByRoleId(roleId);
        return Response.success(menus);
    }

    @GetMapping("/getAll")
    public Response<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.getAllMenus();
        return Response.success(menus);
    }

    @PostMapping("/save")
    public Response<Menu> saveMenu(@RequestBody Menu menu) {
        Menu savedMenu = menuService.saveMenu(menu);
        return Response.success(savedMenu);
    }

    @PostMapping("/update")
    public Response<Void> updateMenu(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
        return Response.success(null);
    }

    @PostMapping("/delete")
    public Response<Void> deleteMenu(@RequestParam Integer id) {
        menuService.deleteMenu(id);
        return Response.success(null);
    }

    @GetMapping("/getById")
    public Response<Menu> getMenuById(@RequestParam Integer id) {
        Menu menu = menuService.getMenuById(id);
        return Response.success(menu);
    }
}
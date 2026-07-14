package edu.xcc.smartcommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.xcc.smartcommunity.common.Response;
import edu.xcc.smartcommunity.entity.dto.PageLikeDTO;
import edu.xcc.smartcommunity.entity.dto.UserLoginDTO;
import edu.xcc.smartcommunity.entity.pojo.User;
import edu.xcc.smartcommunity.entity.vo.UserLoginVO;
import edu.xcc.smartcommunity.entity.vo.UserPageLikeVO;
import edu.xcc.smartcommunity.handler.BizException;
import edu.xcc.smartcommunity.mapper.UserMapper;
import edu.xcc.smartcommunity.service.UserService;
import edu.xcc.smartcommunity.util.JwtUtil;
import jakarta.validation.Valid;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private JwtUtil jwtUtil = new JwtUtil();
    @Autowired// 创建 JwtUtil 对象
    private UserMapper userMapper;

    @PostMapping("/login")
    public Response<UserLoginVO> login(@RequestBody UserLoginDTO dto) {
        UserLoginVO loginVO = userService.login(dto);
        Map<String, String> claims = new HashMap<>();
        claims.put("phone", dto.getPhone());
        String token = jwtUtil.sign(claims, 30);
        loginVO.setToken(token);
        // 查询菜单权限
        UserLoginVO menuListByPhone = userMapper.getMenuListByPhone(dto.getPhone());
        loginVO.setMenuList(menuListByPhone.getMenuList());
        return Response.success(loginVO);
    }

    @GetMapping("/getById")
    public Response<User> getById(@RequestParam Long id) {
        User byId = userService.getById(id);
        return Response.success(byId);
    }

    @GetMapping("/getLikePhone")
    public Response<List<User>> getLikePhone(@RequestParam String phone) {
        List<User> userList = userService.lambdaQuery()
                .like(User::getUserPhone, phone)
                .list();
        return Response.success(userList);
    }

    @PostMapping("/add")
    public Response<User> add(@Valid @RequestBody User user) throws BizException {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        User one = userService.getOne(wrapper.eq(User::getUserPhone, user.getUserPhone()));
        if (one != null) {
            throw new BizException("手机号已经存在，添加失败");
        }
        // 对密码进行加密
        String encodedPassword = encoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);

        boolean save = userService.save(user);
        if (save) {
            return Response.success(user);
        }
        return Response.failed();
    }

    @PostMapping("/deleteById")
    public Response<Void> deleteById(@RequestParam Long id) throws BizException {
        boolean b = userService.removeById(id);
        if (b) {
            return Response.success(null);
        }
        throw new BizException("id不存在，删除失败");
    }

    @PostMapping("/updateById")
    public Response<User> updateById(@RequestBody User user) throws BizException {
        boolean b = userService.updateById(user);
        if (b) {
            return Response.success(user);
        }
        throw new BizException("更新失败，用户不存在");
    }

    @PostMapping("/pageLikePhone")
    public Response<UserPageLikeVO<UserLoginVO>> pageLikePhone(@RequestBody PageLikeDTO dto) {
        // 创建分页对象
        IPage<User> page = new Page<>(dto.getPage(), dto.getSize());

        // 创建条件构造器
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.like(User::getUserPhone, dto.getCondition());

        // 执行分页查询
        userService.page(page, wrapper);

        // 获取结果
        List<User> records = page.getRecords();
        long total = page.getTotal();

        List<UserLoginVO> userVOlist = new ArrayList<>();
        for (User record : records) {
            UserLoginVO vo = new UserLoginVO();
            BeanUtils.copyProperties(record, vo);
            // 手动手机号脱敏：138****8000
            String phone = vo.getUserPhone();
            if (phone != null && phone.length() >= 11) {
                phone = phone.substring(0, 3) + "****" + phone.substring(7);
            }
            vo.setUserPhone(phone);
            userVOlist.add(vo);
        }

        // 封装VO
        UserPageLikeVO<UserLoginVO> vo = new UserPageLikeVO<>();
        vo.setData(userVOlist);
        vo.setTotal(total);

        return Response.success(vo);
    }
    @PostMapping("/deleteBatch")
    public Response deleteBatch(@RequestBody Integer[] ids){
        boolean b = userService.removeByIds(Lists.newArrayList(ids));
        return b? Response.success(null):Response.failed();

    }
}
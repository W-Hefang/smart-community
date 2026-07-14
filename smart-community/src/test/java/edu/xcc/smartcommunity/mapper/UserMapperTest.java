package edu.xcc.smartcommunity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.xcc.smartcommunity.SmartCommunityApplication;
import edu.xcc.smartcommunity.entity.vo.UserLoginVO;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import edu.xcc.smartcommunity.entity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = SmartCommunityApplication.class)
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }



    @Test
    public void testInsert() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUserName("resident2");
        user.setUserPhone("15555540001");
        user.setUserPassword(encoder.encode("123456"));
        user.setUserType("住户");
        int insert = userMapper.insert(user);
        System.out.println("影响的行数: " + insert);
    }

    @Test
    public void testInsertBatch() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setUserName("resident3");
        user1.setUserPhone("15123440002");
        user1.setUserPassword(encoder.encode("123456"));
        user1.setUserType("住户");

        User user2 = new User();
        user2.setUserName("resident4");
        user2.setUserPhone("1501222203");
        user2.setUserPassword(encoder.encode("123456"));
        user2.setUserType("住户");

        userList.add(user1);
        userList.add(user2);

        for (User user : userList) {
            userMapper.insert(user);
        }
        System.out.println("批量插入完成");
    }

    @Test
    public void testDelete() {
        int i = userMapper.deleteById(7);
        Assert.assertEquals(1, i);
    }

    @Test
    public void testDeleteBatch() {
        int i = userMapper.deleteByIds(Lists.list(11, 27));
        Assert.assertEquals(2, i);
    }
    @Test
    public void testUpdate(){
        User user=userMapper.selectById(16);

        if (user !=null){
            user.setUserPhone("15012340002");
            int i = userMapper.updateById(user);
            Assert.assertEquals("修改用户信息失败，请检查用户id是否存在",1,i);
        }
    }
    @Test
    public void testInsertOrUpdate(){
        User user=userMapper.selectById(16);
        userMapper.insertOrUpdate(user);
    }
    @Test
    public void testSelectList1(){
        List<User> userList=userMapper.selectList(null);
        System.out.println(userList);
    }
    @Test
    public void testSelectList2(){
        LambdaQueryWrapper<User> wrapperr = Wrappers.lambdaQuery(User.class);
        wrapperr.like(User::getUserName,"resident");
        wrapperr.or();
        wrapperr.like(User::getUserPhone,"resident");
        List<User> userList=userMapper.selectList(wrapperr);
        System.out.println(userList);
    }
    @Test
    public void testPage(){
        IPage<User> page = new Page<>(1, 2);
        userMapper.selectPage(page, null);
        long total = page.getTotal();
        List<User> records = page.getRecords();
        Assert.assertNotNull(page);
    }
    @Test
    public void testPageLikePhone() {
        // 通过手机号模糊查询+分页+排序
        IPage<User> page = new Page<>(2, 2);

        // 通过条件构造器实现模糊查询
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.like(User::getUserPhone, "135");

        // 排序
        wrapper.orderByDesc(User::getUserId);

        userMapper.selectPage(page, wrapper);
    }
    @Test
    public void testGetMenuListByPhone(){
//        13512345679
        UserLoginVO vo = userMapper.getMenuListByPhone("13512345679");
        Assert.assertNotNull(vo);
    }
}
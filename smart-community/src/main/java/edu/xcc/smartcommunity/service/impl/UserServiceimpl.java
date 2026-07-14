package edu.xcc.smartcommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.xcc.smartcommunity.entity.dto.UserLoginDTO;
import edu.xcc.smartcommunity.entity.pojo.User;
import edu.xcc.smartcommunity.entity.vo.UserLoginVO;
import edu.xcc.smartcommunity.mapper.UserMapper;
import edu.xcc.smartcommunity.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceimpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    private BCryptPasswordEncoder encoder;
   @Override
    public UserLoginVO login(UserLoginDTO loginDTO){
//       获取手机号
        String phone=loginDTO.getPhone();
        String password=loginDTO.getPassword();
       LambdaQueryWrapper<User> wrapper=Wrappers.lambdaQuery(User.class);
       wrapper.eq(User::getUserPhone,phone);
//       wrapper.eq(User::getUserPassword,password);
       User user= userMapper.selectOne(wrapper);
        if(user == null){
            throw new RuntimeException("手机号不正确，登录失败");
        }
        String encodedPassword = user.getUserPassword();
       encoder = new BCryptPasswordEncoder();
       boolean matches = encoder.matches(password, encodedPassword);
       if(!matches){
           throw new RuntimeException("登录失败，请检查手机号或密码");
       }
       UserLoginVO loginVO= new UserLoginVO();
       BeanUtils.copyProperties(user,loginVO);
        return loginVO;
    }
}

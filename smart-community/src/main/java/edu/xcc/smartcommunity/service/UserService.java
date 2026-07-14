package edu.xcc.smartcommunity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.xcc.smartcommunity.entity.dto.UserLoginDTO;
import edu.xcc.smartcommunity.entity.pojo.User;
import edu.xcc.smartcommunity.entity.vo.UserLoginVO;

public interface UserService extends IService<User> {
    UserLoginVO login(UserLoginDTO loginDTO);
}
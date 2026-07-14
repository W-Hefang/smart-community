package edu.xcc.smartcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.xcc.smartcommunity.entity.pojo.User;
import edu.xcc.smartcommunity.entity.vo.UserLoginVO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    UserLoginVO getMenuListByPhone(@Param("phone") String phone);
}
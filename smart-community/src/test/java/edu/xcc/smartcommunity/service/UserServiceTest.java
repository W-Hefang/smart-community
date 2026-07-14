package edu.xcc.smartcommunity.service;

import edu.xcc.smartcommunity.SmartCommunityApplication;
import edu.xcc.smartcommunity.entity.dto.UserLoginDTO;
import edu.xcc.smartcommunity.entity.vo.UserLoginVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SmartCommunityApplication.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testLogin(){
        UserLoginDTO dto = new UserLoginDTO();
        dto.setPhone("18888888888");
        dto.setPassword("123456");
        UserLoginVO loginVO = userService.login(dto);

        Assert.assertNotNull(loginVO);
    }
}

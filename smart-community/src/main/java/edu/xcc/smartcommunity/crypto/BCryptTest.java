package edu.xcc.smartcommunity.crypto;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    @Test
    public void testEncode(){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encode=encoder.encode("123456");
        System.out.println("加密后的结果："+encode);
    }
    @Test
    public void testMatch(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean result =encoder.matches("123456","$2a$10$gYB7hUuHpL0OgJWemhqU6OCMLgeT1s/b4Rfe0cSKZ49KfTryKLTbS");
        System.out.println("result:"+result);

//       Assert.assertTrue("密码校验不通过",result);
    }
}

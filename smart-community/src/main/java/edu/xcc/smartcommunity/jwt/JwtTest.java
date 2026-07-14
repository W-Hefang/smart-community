package edu.xcc.smartcommunity.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import edu.xcc.smartcommunity.util.JwtUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class JwtTest {
    @Test
    public void encode(){
        JWTCreator.Builder builder= JWT.create();
        builder.withClaim("phone","13312345678");
        builder.withClaim("username","ADMIN");
        builder.withExpiresAt(new Date(System.currentTimeMillis()+5000*60*1000));
        String jwtString=builder.sign(Algorithm.HMAC256("BL123456@"));
        System.out.println(jwtString);
    }
    @Test
    public void testVerity(){
        String rawtoken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZSI6IjEzMzEyMzQ1Njc4IiwidXNlcm5hbWUiOiJBRE1JTiIsImV4cCI6MTc3ODQ4Mzc0MH0.DzH23HeowW_1gdzU7eKhsNpHqYq9-dVRhj5Z9PmWido";
        Verification verification = JWT.require(Algorithm.HMAC256("BL123456@"));
        JWTVerifier build = verification.build();
        build.verify(rawtoken);
    }
    @Test
    public void testGetClaim(){
        String rawtoken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZSI6IjEzMzEyMzQ1Njc4IiwidXNlcm5hbWUiOiJBRE1JTiIsImV4cCI6MTc3ODQ4Mzc0MH0.DzH23HeowW_1gdzU7eKhsNpHqYq9-dVRhj5Z9PmWido";
        String phone = JwtUtil.getClaim(rawtoken, "phone");
        System.out.println("phone"+phone);
    }
}

package edu.xcc.smartcommunity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String SECURTY="BL123456@";
    public static String sign(Map<String,String> claims, Integer minute){
        JWTCreator.Builder builder = JWT.create();
        claims.forEach((k,v)->{
            builder.withClaim(k,v);

        });
        builder.withExpiresAt(new Date(System.currentTimeMillis()+minute*60000));
        return builder.sign(Algorithm.HMAC256(SECURTY));
    }
    public static boolean verify(String rawToken){
        try{
        Verification verification = JWT.require(Algorithm.HMAC256(SECURTY));
        JWTVerifier jwtVerifier = verification.build();
        jwtVerifier.verify(rawToken);
        return true;
    }catch (Exception e){
            return false;
        }
}
public static  String getClaim(String jwt,String claimName){
    DecodedJWT decode = JWT.decode(jwt);
    return  decode.getClaim(claimName).asString();
}
}

package com.upc.srp.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.upc.srp.model.User;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author by zhoutao
 * @description JWT token类
 * @date 2019/12/27 10:10
 */
public class JWTUtil {

    // 过期时间 2h
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;

    /**
     * @description 验证token
     * @param token，user
     * @return boolean
     * @author zhoutao
     * @date 2019/12/27 10:13
     */
    public static boolean verify(String token, User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", user.getUsername()).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (JWTVerificationException e){
            System.err.println("token认证失败，详情：" + e.toString());
            return false;
        }
    }


    /**
     * @description 生成token
     * @param user
     * @return String
     * @author zhoutao
     * @date 2019/12/27 10:23
     */
    public static String generateToken(User user){
        try{
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            // 附带username信息
            return JWT.create()
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch(JWTCreationException e){
            System.err.println("token生成失败,详情："+ e.toString());
            return null;
        }
    }
}

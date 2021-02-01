package com.njeport.track.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.njeport.track.common.constants.StringConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author kongming
 * @date 2019/12/18
 */
@Slf4j
public class JwtUtils {

    /**
     * 从 token 解析出 username 信息
     *
     * @return jwt token 内容
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(StringConstants.USERNAME).asString();
        } catch (JWTDecodeException e) {
            log.error("未能从token解析出用户名", e);
            return null;
        }
    }

    /**
     * 校验token
     *
     * @param token  jwt token
     * @param secret 密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(StringConstants.USERNAME, username).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
//            log.error("未能通过token校验, token = " + token);
            return false;
        }
    }

    /**
     * 生成签名
     *
     * @param username 用户名
     * @param secret   密钥
     * @return 签名
     */
    public static String sign(String username, String secret, long expireTime) {
        Date date = new Date(System.currentTimeMillis() + expireTime);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withClaim(StringConstants.USERNAME, username)
                .withExpiresAt(date).sign(algorithm);
    }
}

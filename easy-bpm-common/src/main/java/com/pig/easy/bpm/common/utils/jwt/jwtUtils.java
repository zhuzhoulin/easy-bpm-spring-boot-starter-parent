package com.pig.easy.bpm.common.utils.jwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.UUID;

public class jwtUtils {

    public static final String SECRET_KEY = "easybpmdfagsgfhuhgirgvbmcknzdjkdfgdfb"; //秘钥
    public static final long TOKEN_EXPIRE_TIME = 6 * 60 * 60 * 1000; //token过期时间
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 2 * 60 * 60 * 1000; //refreshToken过期时间
    private static final String ISSUER = "easybpm"; //签发人

    /**
     * 生成签名
     */
    public static String generateToken(Long userId) {
        return generateToken(SECRET_KEY,TOKEN_EXPIRE_TIME,ISSUER,userId);
    }

    /**
     * 功能描述: 生成JWT token
     *
     *
     * @param secretKey 秘钥
     * @param tokenExpireTime 过期时间
     * @param issuer 签发人
     * @param userId 工号
     * @return : java.lang.String
     * @author : pig
     * @date : 2020/5/15 11:55
     */
    public static String generateToken(String secretKey,long tokenExpireTime, String issuer,Long userId) {
        long nowMillis = System.currentTimeMillis();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer(issuer)
                .withIssuedAt(new Date(nowMillis))
                .withExpiresAt(new Date(nowMillis + tokenExpireTime))
                .withClaim("userId", userId)
                .sign(algorithm);
    }


    public static boolean verify(String secretKey,String  issuer,String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 验证token
     */
    public static boolean verify(String token) {
        return verify(SECRET_KEY,ISSUER,token);
    }

    /**
     * 从token获取userId
     */
    public static Long getUserId(String token) {
        try {
            return JWT.decode(token).getClaim("userId").asLong();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1000L;
    }


}

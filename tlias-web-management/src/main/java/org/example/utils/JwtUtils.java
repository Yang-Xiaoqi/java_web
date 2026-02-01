package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 */
public class JwtUtils {

    // 签名密钥（Base64 编码字符串，与测试用例一致）
    private static final String SECRET_KEY = "aXRoZWltYQ==";

    // 令牌过期时间：12 小时（单位：毫秒）
    private static final long EXPIRATION = 12 * 3600 * 1000; // 12h

    /**
     * 生成 JWT 令牌
     *
     * @param claims 自定义载荷（如 id, username 等）
     * @return JWT 字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .compact();
    }

    /**
     * 解析 JWT 令牌
     *
     * @param token JWT 字符串
     * @return Claims 对象（包含 payload 数据）
     * @throws RuntimeException 当 token 无效、过期或签名错误时抛出
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("无效或已过期的 JWT 令牌", e);
        }
    }
}
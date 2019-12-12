package com.squeaker.entry.utils;

import com.squeaker.entry.exception.ExpiredTokenException;
import com.squeaker.entry.exception.InvalidTokenException;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private static final String SECURITY_KEY = "this_is_password";// 하루동안 토큰 유지

    private static String generateToken(Object data, Long expire) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(data.toString())
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expire))
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY);

        return builder.compact();
    }

    public static String getAccessToken(Object data) {
        return generateToken(data, 1000L * 3600 * 24);
    }
    public static String getRefreshToken(Object data) {
        return generateToken(data, 1000L * 3600 * 24 * 30);
    }

    public static String parseToken(String token) {
        try {
            token = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
        return token;
    }
}

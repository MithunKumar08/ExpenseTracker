package com.mithun.expensetracker.security;

import com.mithun.expensetracker.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    private final String SECRET_KEY = "mithuntejas08kumar2003rajashekar17mamatha1986";

    public SecretKey getSECRET_KEY(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId",user.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSECRET_KEY())
                .compact();
    }

    public String getUserNameFromToken(String token) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(getSECRET_KEY())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claim.getSubject();

    }
}

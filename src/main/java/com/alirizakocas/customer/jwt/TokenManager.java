package com.alirizakocas.customer.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class TokenManager {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.exp}")
    private int exp;

    private Key getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        System.out.println(Keys.hmacShaKeyFor(keyBytes).getAlgorithm());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username){

        return Jwts.builder().
                setSubject(username). // gelen username'i subject olarak atadık
                        setExpiration(new Date(System.currentTimeMillis() + exp*1000)). // token ne zamana kadar geçerli
                        setIssuer("http://localhost:8080/create/customer"). // token'ı imzalayan
                        setIssuedAt(new Date(System.currentTimeMillis())). // token oluşturma zamanı
                        signWith(getSigningKey()).
                compact();
    }

    public boolean tokenValidate(String token){
        if(getUsernameFromToken(token) != null && isExpired(token)){
            return true;
        }
        return false;
    }

    public String getUsernameFromToken(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
}

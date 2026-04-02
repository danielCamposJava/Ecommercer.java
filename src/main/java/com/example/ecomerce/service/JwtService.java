package com.example.ecomerce.service;

import com.example.ecomerce.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private  final String SECRETE = "my-secret-key-my-secret-key ";

    public  String generateToken(UserEntity user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt( new Date())
                .setExpiration( new Date( System.currentTimeMillis() + 1000 * 60 * 90))
                .signWith(getSingleKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public  String extractEmail(String token){

        return  Jwts.parserBuilder().setSigningKey(getSingleKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    private Key getSingleKey(){
        return Keys.hmacShaKeyFor(SECRETE.getBytes());
    }

}

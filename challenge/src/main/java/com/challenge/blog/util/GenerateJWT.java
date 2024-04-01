package com.challenge.blog.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class GenerateJWT {

    public static String createToken(String user){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                //Set Header
                .setHeaderParam("type", "JWT")
                .setHeaderParam("alg", "HS256")
                //Set payload classes
                .setId(user)
                .setIssuedAt(now)
                .setSubject("bootcamp")
                .setIssuer("EKSAD")
                .setExpiration(new Date(System.currentTimeMillis()+1*300*1000))
                .signWith(SignatureAlgorithm.HS256, "PASSWORD");

        return builder.compact();

    }

    public static Claims validateToken(String token){

        Claims claims = null;

        claims = Jwts.parser()
                .setSigningKey("PASSWORD")
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}

package com.itsrdb.bookmytrain.BookMyTrain.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtils {

    public static String extractUsername(String token, String secret) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public static void main(String[] args) {
        String jwtToken = "your_jwt_token_here";
        String secretKey = "your_secret_key_here";

        String username = extractUsername(jwtToken, secretKey);
        System.out.println("Username: " + username);
    }
}

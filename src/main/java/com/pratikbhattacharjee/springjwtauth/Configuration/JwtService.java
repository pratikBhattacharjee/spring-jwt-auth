package com.pratikbhattacharjee.springjwtauth.Configuration;

import java.security.Key;
import java.util.Base64.Decoder;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "B113B9C65F491DB9E35DBF6DABB88";


    public String extractUsername(String token) {
        // Implement the logic for extracting the username from the jwt.
        //the getSubject method returns the subject of the jwt which is the email or username or clientID or whateva
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Implement the logic for extracting the claim from the jwt.
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // Implement the logic for extracting all the claims from the jwt.
        return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    private Key getSignInKey(){
        // Implement the logic for getting the signing key.
        //Getting individual bytes of the key
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

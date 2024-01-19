package com.pratikbhattacharjee.springjwtauth.Configuration;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "B113B9C65F491DB9E35DBF6DABB88";

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Implement the logic for generating the jwt.

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))//10 hours
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        // Implement the logic for validating the jwt.
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        // Implement the logic for checking if the token is expired.
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        // Implement the logic for extracting the expiration date from the jwt.
        return extractClaim(token, Claims::getExpiration);
    }

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

package com.michael.socialmedia.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtFilter {



    @Value("${jwt.expiration.access-token}")
    private long access_expiration;

    @Value("${jwt.expiration.refresh-token}")
    private long  refresh_token;
    public String extractUsername(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder()
                .setSigningKey(generatedkey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Date extractExpirationDate(String token){
        return  extractClaims(token,Claims::getExpiration);
    }
    private <T>  T extractClaims(String token, Function<Claims,T> claimsTFunction){
        Claims claims =extractAllClaim(token);
      return   claimsTFunction.apply(claims);
    }

    private Key generatedkey() {
        byte [] secreteKey = DatatypeConverter.parseBase64Binary(generatedSecretKey());
        return new SecretKeySpec(secreteKey,"HmacSHA512");
    }

    private String generatedSecretKey() {
        return  DatatypeConverter.printBase64Binary(new byte[512/8]);
    }


    public String generateAccessToken(UserDetails userDetails){
        return  buildTokens(new HashMap<>(),userDetails,access_expiration);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return buildTokens(new HashMap<>(),userDetails,refresh_token);

    }

    public String buildTokens(Map<String, Object> extractClaims ,
                              UserDetails userDetails ,Long expiration
                              ){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(generatedkey(), SignatureAlgorithm.HS256)
                .compact();

    }




    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
      }


      private boolean isTokenExpired(String token) {
       return extractExpirationDate(token).before(new Date());
       }


}

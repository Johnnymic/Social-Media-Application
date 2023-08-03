package com.michael.socialmedia.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@RequiredArgsConstructor
public class JwtFilter {

//    @Value("${application.security.jwt.secreteKey}")
    private  String secreteKey ="655468576D5A7134743777397A24432646294A404E635266556A586E32723575";

    @Value("${jwt.expiration.access-token}")
    private long access_expiration;

    @Value("${jwt.expiration.refresh-token}")
    private long  refresh_token;
    public String extractUsername(String token) {
        return extractClaims(token,Claims::getSubject);
    }
    private Key generatedkey() {
        byte[] signKey = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(signKey);
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
    private  <T> T extractClaims(String token , Function<Claims, T> claimsRevolver){
        Claims claims =extractAllClaim(token);
        return  claimsRevolver.apply(claims);
    }

//    private Key generatedkey() {
//        byte[]  secretKey = DatatypeConverter.parseBase64Binary(generateSecret());
//        return new SecretKeySpec(secretKey, "HmacSHA512");
//    }
//    private String generateSecret() {
//        return DatatypeConverter.printBase64Binary(new byte[512/8]);
//    }


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
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .signWith(generatedkey(), SignatureAlgorithm.HS256)
                .compact();

    }




    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
      }


      private boolean isTokenExpired(String token) {
       return extractExpirationDate(token).after(new Date());
       }


}

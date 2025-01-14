package com.bash.taskmanager.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.Signature;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * NOT IN USE
 */
@Service
public class JwtService {

    private static final String secretKey = "8f561aed8ae5c0cc3003cfebe7493382b2aaff1b61641a9c9cdbb43b8ac4af471bbb9e3549e6dab83542c700065b42b3930f2dc616e03ea0de89ec43d365aba3";
     public String extractUsername (String token){
         return extractClaims(token, Claims::getSubject);
     }

     public String generateToken(UserDetails userDetails){
         return generateToken(new HashMap<>(),userDetails);
     }

     public boolean isTokenValid(String token, UserDetails userDetails){
         final String username = extractUsername(token);
         return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
     }

     public boolean isTokenExpired(String token){
         return extractExpiration(token).before(new Date());
     }

     private Date extractExpiration(String token){
         return extractClaims(token, Claims::getExpiration);
     }

     public String generateToken(Map<String, Object> extraClaims, UserDetails userdetails){
         return Jwts
                 .builder()
                 .setClaims(extraClaims)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis()))
                 .signWith(SignatureAlgorithm.HS256, getSigninKey())
                 .compact();
     }

     public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
         final Claims claims = extractAllClaims(token);
         return claimsResolver.apply(claims);
     }
     private Claims extractAllClaims(String token){
         return Jwts
                 .parser()
                 .setSigningKey(getSigninKey())
                 .parseClaimsJws(token)
                 .getBody();
     }

    private Key getSigninKey() {
         byte [] keyBytes = Decoders.BASE64.decode(secretKey);
         return Keys.hmacShaKeyFor(keyBytes);
    }
}

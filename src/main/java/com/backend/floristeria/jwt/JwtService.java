package com.backend.floristeria.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.backend.floristeria.exception.JwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;

@Service
public class JwtService {

	@Value("${application.security.jwt.secret-key}")
    private String secret_key;
    private static final long REFRESH_TOKEN_VALIDITY = 360000000;
    
    @Value("${jwt.token.expiration.minutes}")
    private int tokenExpirationMinutes;

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

	private String getToken(Map<String,Object> extraClaims, UserDetails user) {  
		long expirationMillis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(tokenExpirationMinutes);
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(expirationMillis))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getKey() {
       byte[] keyBytes=Decoders.BASE64.decode(secret_key);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        boolean isValid = false;
        try {
            if (username.equals(userDetails.getUsername())) {
                isValid = !isTokenExpired(token);
            }
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token expired", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new JwtException("Error parsing JWT", HttpStatus.BAD_REQUEST);
        }
        return isValid;
    }



    private Claims getAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
    
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para validar el token de actualización
    public boolean isRefreshTokenValid(String refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para obtener el nombre de usuario desde el token de actualización
    public String getUsernameFromRefreshToken(String refreshToken) {
        return getClaim(refreshToken, Claims::getSubject);
    }
    
    public String refreshToken(String token) {
        return "";
    }
    
}
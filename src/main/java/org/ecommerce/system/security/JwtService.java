package org.ecommerce.system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.ecommerce.system.application.infrastructure.EntityProperties;
import org.ecommerce.system.application.service.BaseRedisService;
import org.ecommerce.system.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {
    private final BaseRedisService redisService;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refresh-expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;

    public JwtService(BaseRedisService redisService) {
        this.redisService = redisService;
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        claims.put("fullName", user.getFullName());
        return generateToken(claims, user);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserEntity userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String createRefreshToken(UserEntity userEntity) {

        String token = UUID.randomUUID().toString();
        String key = EntityProperties.REFRESH_TOKEN + userEntity.getUsername();

        redisService.delete(key);
        redisService.set(key, token);
        redisService.setTimeToLive(key, REFRESH_TOKEN_EXPIRATION / 1000);

        redisService.set(EntityProperties.REFRESH_MAPPING + token, userEntity.getUsername());
        redisService.setTimeToLive(EntityProperties.REFRESH_MAPPING + token, REFRESH_TOKEN_EXPIRATION / 1000);

        return token;
    }

    public boolean validRefreshToken(String token) {
        if (redisService.exists(EntityProperties.BLACK_LIST + token)) {
            return false;
        }
        String username = (String) redisService.get(EntityProperties.REFRESH_MAPPING + token);
        if (username == null) {
            return false;
        }
        String storedToken = redisService.get(EntityProperties.REFRESH_TOKEN + username).toString();
        return token.equals(storedToken);
    }

    public void invalidateRefreshToken(String token, String username) {
        redisService.set(EntityProperties.BLACK_LIST + token, "true");
        redisService.setTimeToLive(EntityProperties.BLACK_LIST + token, REFRESH_TOKEN_EXPIRATION / 1000);
        if (username != null) {
            redisService.delete(EntityProperties.REFRESH_MAPPING + token);
            redisService.delete(EntityProperties.REFRESH_TOKEN + username);
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.gyh.fleacampus.common;

import com.gyh.fleacampus.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Collection;
import java.util.Date;

/**
 * Created by gyh on 2021/2/4
 */
public class JwtUtil {
    private static final Key key = new SecretKeySpec(
            ("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdlatRjRjogo3WojgGHFHYLugd\n" +
            "UWAY9iR3fy4arWNA1KoS8kVw33cJibXr8bvwUAUparCwlvdbH6dvEOfou0/gCFQs\n" +
            "HUfQrSDv+MuSUMAe8jzKE4qW+jK+xQU9a03GUnKHkkle+Q0pX/g6jXZ7r1/xAK5D\n" +
            "o2kQ+X5xK9cipRgEKwIDAQAB").getBytes(),
            SignatureAlgorithm.HS256.getJcaName());
    //60秒     分    时   天
    private static final long ttlMillis = 60_000 * 60 * 24 * 7;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public static User parseToken(String token)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        User user = new User();
        user.setId((Integer) claims.get("id"));
        user.setUsername((String) claims.get("username"));
        user.setRoles((Collection<String>) claims.get("roles"));
        return user;
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(User u) {
        Claims claims = Jwts.claims();
        claims.put("id", u.getId());
        claims.put("username", u.getUsername());
        claims.put("roles", u.getRoles());
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + ttlMillis))
                .addClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

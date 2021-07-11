package com.gyh.fleacampus.common

import io.jsonwebtoken.*
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

/**
 * Created by gyh on 2021/2/4
 */
object JwtUtil {
    @JvmStatic
    private val key: Key = SecretKeySpec(
        """VFVsSFprMUJNRWREVTNGSFUwbGlNMFJSUlVKQlVWVkJRVFJIVGtGRVEwSnBVVXRDWjFGRVpHeGhkRkpxVW1wdloyOHpWMjlxWjB
            |kSVJraFpUSFZuWkFwVlYwRlpPV2xTTTJaNU5HRnlWMDVCTVV0dlV6aHJWbmN6TTJOS2FXSlljamhpZG5kVlFWVndZWEpEZDJ4
            |MlpHSklObVIyUlU5bWIzVXdMMmREUmxGekNraFZabEZ5VTBSMkswMTFVMVZOUVdVNGFucExSVFJ4Vnl0cVN5dDRVVlU1WVRBe
            |lIxVnVTMGhyYTJ4bEsxRXdjRmd2WnpacVdGbzNjakV2ZUVGTE5VUUtiekpyVVN0WU5YaExPV05wY0ZKblJVdDNTVVJCVVVGQwog"""
            .trimMargin().toByteArray(),
        SignatureAlgorithm.HS256.jcaName
    )

    //60秒     分    时   天
    const val ttlMillis = 30000L

    private const val refreshTtl = 60000L

    /**
     * Tries to parse specified String as a JWT token. If successful, returns BaseUser object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the BaseUser object extracted from specified token or null if a token is invalid.
     */
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T : BaseUser> parseToken(token: String?, user: T): T {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        user.id = (claims["id"] as Int)
        user.setUsername(claims["username"] as String)
        user.setRoles(claims["roles"] as Collection<String>)
        return user
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * BaseUser object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    @JvmStatic
    fun generateToken(u: BaseUser): String {
        return generateToken(u, ttlMillis, key)
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun generateRefreshToken(u: BaseUser, token: String?): String? {
        val claims = try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
        u.id = (claims["id"] as Int)
        u.setUsername(claims["username"] as String)
        u.setRoles(claims["roles"] as Collection<String>)
        return if (claims.expiration > Date(System.currentTimeMillis() - refreshTtl)) {
            generateToken(u)
        } else null
    }

    @JvmStatic
    fun generateToken(u: BaseUser, ttl: Long, key: Key): String {
        val claims = Jwts.claims()
        claims["id"] = u.id
        claims["username"] = u.getUsername()
        claims["roles"] = u.getRoles()
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + ttl))
            .addClaims(claims)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}
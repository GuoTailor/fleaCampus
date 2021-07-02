package com.gyh.fleacampus.core.common

import com.gyh.fleacampus.model.User
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
        """TUlHZk1BMEdDU3FHU0liM0RRRUJBUVVBQTRHTkFEQ0JpUUtCZ1FEZGxhdFJqUmpvZ28zV29qZ0dIRkhZTHVnZApVV0FZOWlSM2Z5NGFyV05BM
            |UtvUzhrVnczM2NKaWJYcjhidndVQVVwYXJDd2x2ZGJINmR2RU9mb3UwL2dDRlFzCkhVZlFyU0R2K011U1VNQWU4anpLRTRxVytqSyt4UVU5
            |YTAzR1VuS0hra2xlK1EwcFgvZzZqWFo3cjEveEFLNUQKbzJrUStYNXhLOWNpcFJnRUt3SURBUUFC""".trimMargin().toByteArray(),
        SignatureAlgorithm.HS256.jcaName
    )

    //60秒     分    时   天
    private const val ttlMillis = (60000 * 60 * 24 * 7).toLong()

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    @JvmStatic
    fun parseToken(token: String?): User {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        val user = User()
        user.id = claims["id"] as Int?
        user.setUsername(claims["username"] as String)
        user.setRoles(claims["roles"] as Collection<String>)
        return user
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    @JvmStatic
    fun generateToken(u: User): String {
        val claims = Jwts.claims()
        claims["id"] = u.id
        claims["username"] = u.username
        claims["roles"] = u.getRoles()
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + ttlMillis))
            .addClaims(claims)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}
package com.github.dankook_univ.auth.application.service

import com.github.dankook_univ.auth.application.port.`in`.TokenUseCase
import com.github.dankook_univ.auth.application.port.out.dto.TokenPort
import com.github.dankook_univ.auth.domain.Auth
import com.github.dankook_univ.auth.domain.Token
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Key
import java.util.*

@Service
@Transactional(readOnly = true)
class TokenService(
    @Value("\${jwt.secret}")
    private val secretKey: String,

    @Value("\${jwt.expired.access}")
    private val accessTokenExpiredDate: Long,

    @Value("\${jwt.expired.refresh}")
    private val refreshTokenExpiredDate: Long,

    private val tokenPort: TokenPort,
) : TokenUseCase {

    private val key: Key

    init {
        key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    }

    @Transactional
    override fun create(auth: Auth): Token {
        val currentTime = Date().time

        val accessToken = Jwts.builder()
            .setClaims(setClaims(auth))
            .setSubject(auth.user.id.toString())
            .setExpiration(Date(currentTime + accessTokenExpiredDate))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken = Jwts.builder()
            .setClaims(setClaims(auth, refresh = true))
            .setSubject(auth.user.id.toString())
            .setExpiration(Date(currentTime + refreshTokenExpiredDate))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        return tokenPort.save(
            auth.id!!,
            Token(
                accessToken = accessToken,
                refreshToken = refreshToken
            ),
            refreshTokenExpiredDate
        )
    }

    override fun parseUserId(token: String): Long {
        return getClaims(token).get("userId", String::class.java).toLong()
    }

    override fun validation(token: String?): Authentication? {
        if (token == null || tokenPort.getByAuthId(parseUserId(token)) == null) {
            return null
        }

        val claims = getClaims(token)
        val authorities = listOf(SimpleGrantedAuthority("General"))

        return UsernamePasswordAuthenticationToken(
            User(claims.subject, "", authorities),
            token,
            authorities
        )
    }

    override fun isRefreshToken(token: String): Boolean {
        return getClaims(token).get("refresh", String::class.java).toBoolean()
    }

    @Transactional
    override fun delete(auth: Auth) {
        tokenPort.delete(auth.id!!)
    }

    private fun setClaims(auth: Auth, refresh: Boolean = false): Claims {
        val claims = Jwts.claims()

        claims["userId"] = auth.user.id.toString()
        if (refresh) {
            claims["refresh"] = true.toString()
        }

        return claims
    }

    private fun getClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}
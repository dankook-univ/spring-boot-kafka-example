package com.github.dankook_univ.auth.application.port.`in`

import com.github.dankook_univ.auth.domain.Auth
import com.github.dankook_univ.auth.domain.Token
import org.springframework.security.core.Authentication

interface TokenUseCase {
    fun create(auth: Auth): Token
    fun parseUserId(token: String): Long
    fun validation(token: String?): Authentication?
    fun isRefreshToken(token: String): Boolean
    fun delete(auth: Auth)
}
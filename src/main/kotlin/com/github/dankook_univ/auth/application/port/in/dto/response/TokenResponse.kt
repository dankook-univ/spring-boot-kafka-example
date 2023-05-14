package com.github.dankook_univ.auth.application.port.`in`.dto.response

import com.github.dankook_univ.auth.domain.Token
import jakarta.validation.constraints.NotBlank

class TokenResponse(
    token: Token
) {
    @NotBlank
    val accessToken: String = token.accessToken

    @NotBlank
    val refreshToken: String = token.refreshToken
}
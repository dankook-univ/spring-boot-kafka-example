package com.github.dankook_univ.auth.application.port.`in`.dto.response

import com.github.dankook_univ.auth.domain.Auth
import com.github.dankook_univ.user.adapter.`in`.web.dto.UserResponse
import jakarta.validation.constraints.NotNull


class AuthResponse(
    auth: Auth,
) {
    @NotNull
    val user: UserResponse = auth.user.toResponse()

    @NotNull
    val token: TokenResponse? = auth.token?.toResponse()
}
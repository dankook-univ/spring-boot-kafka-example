package com.github.dankook_univ.auth.application.port.`in`.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignUpRequest(
    @Email
    @NotBlank
    val email: String,

    @NotBlank
    val password: String,

    @NotBlank
    val nickname: String,
)

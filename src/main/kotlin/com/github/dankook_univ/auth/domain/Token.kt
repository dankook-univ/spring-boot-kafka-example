package com.github.dankook_univ.auth.domain

import com.github.dankook_univ.auth.application.port.`in`.dto.response.TokenResponse
import java.io.Serializable

class Token(
    val accessToken: String,
    val refreshToken: String,
) : Serializable {
    constructor() : this("", "")

    fun toResponse(): TokenResponse = TokenResponse(this)
}
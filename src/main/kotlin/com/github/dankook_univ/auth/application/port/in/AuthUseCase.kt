package com.github.dankook_univ.auth.application.port.`in`

import com.github.dankook_univ.auth.application.port.`in`.dto.request.SignInRequest
import com.github.dankook_univ.auth.application.port.`in`.dto.request.SignUpRequest
import com.github.dankook_univ.auth.domain.Auth

interface AuthUseCase {
    fun signIn(request: SignInRequest): Auth
    fun signUp(request: SignUpRequest): Auth
    fun signOut(userId: Long)
    fun reissue(refreshToken: String): Auth
}
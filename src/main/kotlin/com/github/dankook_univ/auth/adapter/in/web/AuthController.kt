package com.github.dankook_univ.auth.adapter.`in`.web

import com.github.dankook_univ.auth.application.port.`in`.AuthUseCase
import com.github.dankook_univ.auth.application.port.`in`.dto.request.SignInRequest
import com.github.dankook_univ.auth.application.port.`in`.dto.request.SignUpRequest
import com.github.dankook_univ.auth.application.port.`in`.dto.response.AuthResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/auth")
class AuthController(
    private val authUseCase: AuthUseCase,
) {
    @PostMapping("/login")
    fun signIn(
        @Valid @RequestBody request: SignInRequest
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authUseCase.signIn(request).toResponse())
    }

    @PostMapping("/new")
    fun signUp(
        @Valid @RequestBody request: SignUpRequest,
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authUseCase.signUp(request).toResponse())
    }

    @DeleteMapping("/logout")
    fun signOut(
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(authUseCase.signOut(authentication.name.toLong()))
    }

    @PostMapping("/reissue")
    fun refreshToken(
        @Valid @NotBlank @RequestBody refreshToken: String,
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authUseCase.reissue(refreshToken).toResponse())
    }
}
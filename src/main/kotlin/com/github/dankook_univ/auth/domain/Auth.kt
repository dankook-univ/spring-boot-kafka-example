package com.github.dankook_univ.auth.domain

import Core
import com.github.dankook_univ.auth.application.port.`in`.dto.response.AuthResponse
import com.github.dankook_univ.user.domain.User
import jakarta.persistence.*

@Entity
class Auth(
    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @OneToOne(targetEntity = User::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : Core() {
    @Transient
    var token: Token? = null

    fun toResponse(): AuthResponse = AuthResponse(this)
}

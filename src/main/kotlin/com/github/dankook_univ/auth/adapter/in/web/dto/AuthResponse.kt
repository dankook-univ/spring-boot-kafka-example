package com.github.dankook_univ.auth.adapter.`in`.web.dto

import java.time.LocalDateTime

data class AuthResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

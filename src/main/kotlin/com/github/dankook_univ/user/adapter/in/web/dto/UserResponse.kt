package com.github.dankook_univ.user.adapter.`in`.web.dto

import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val nickname: String,
)

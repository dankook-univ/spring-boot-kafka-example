package com.github.dankook_univ.user.domain

import Core
import com.github.dankook_univ.user.adapter.`in`.web.dto.UserResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "member")
class User(
    @Column(nullable = false, unique = true)
    val nickname: String,
) : Core() {
    fun toResponse(): UserResponse {
        return UserResponse(
            id = this.id!!,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            nickname = this.nickname
        )
    }
}
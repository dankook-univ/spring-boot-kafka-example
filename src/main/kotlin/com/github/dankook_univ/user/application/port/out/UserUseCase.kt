package com.github.dankook_univ.user.application.port.out

import com.github.dankook_univ.user.application.port.`in`.dto.CreateUserRequest
import com.github.dankook_univ.user.domain.User

interface UserUseCase {
    fun getByNickname(nickname: String): User?
    fun create(request: CreateUserRequest): User
}
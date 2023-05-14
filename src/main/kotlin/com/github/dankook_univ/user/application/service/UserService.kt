package com.github.dankook_univ.user.application.service

import com.github.dankook_univ.user.application.port.`in`.UserPort
import com.github.dankook_univ.user.application.port.`in`.dto.CreateUserRequest
import com.github.dankook_univ.user.application.port.out.UserUseCase
import com.github.dankook_univ.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userPort: UserPort,
) : UserUseCase {
    override fun getByNickname(nickname: String): User? {
        return userPort.getByNickname(nickname)
    }

    @Transactional
    override fun create(request: CreateUserRequest): User {
        return userPort.create(request)
    }
}
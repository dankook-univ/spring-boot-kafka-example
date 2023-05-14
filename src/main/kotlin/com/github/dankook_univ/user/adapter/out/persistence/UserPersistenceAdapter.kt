package com.github.dankook_univ.user.adapter.out.persistence

import com.github.dankook_univ.user.application.port.`in`.UserPort
import com.github.dankook_univ.user.application.port.`in`.dto.CreateUserRequest
import com.github.dankook_univ.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserPort {
    override fun getByNickname(nickname: String): User? {
        return userJpaRepository.findByNickname(nickname)
    }

    override fun create(request: CreateUserRequest): User {
        return userJpaRepository.save(
            User(nickname = request.nickname)
        )
    }
}
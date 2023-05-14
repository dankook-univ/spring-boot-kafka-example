package com.github.dankook_univ.user.adapter.out.persistence

import com.github.dankook_univ.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long> {
    fun findByNickname(nickname: String): User?
}
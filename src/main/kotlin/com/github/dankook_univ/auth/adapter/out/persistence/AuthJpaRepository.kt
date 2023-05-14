package com.github.dankook_univ.auth.adapter.out.persistence

import com.github.dankook_univ.auth.domain.Auth
import org.springframework.data.jpa.repository.JpaRepository

interface AuthJpaRepository : JpaRepository<Auth, Long> {
    fun findByEmail(email: String): Auth?
    fun findByUserId(userId: Long): Auth?
}
package com.github.dankook_univ.auth.adapter.out.persistence

import com.github.dankook_univ.auth.application.port.out.dto.AuthPort
import com.github.dankook_univ.auth.domain.Auth
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class AuthPersistenceAdapter(
    private val authJpaRepository: AuthJpaRepository,
) : AuthPort {

    @Transactional
    override fun save(auth: Auth): Auth {
        return authJpaRepository.save(auth)
    }

    @Transactional
    override fun getByEmail(email: String): Auth? {
        return authJpaRepository.findByEmail(email)
    }

    override fun getByUserId(userId: Long): Auth? {
        return authJpaRepository.findByUserId(userId)
    }
}




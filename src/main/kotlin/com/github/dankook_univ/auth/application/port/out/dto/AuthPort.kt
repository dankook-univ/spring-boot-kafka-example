package com.github.dankook_univ.auth.application.port.out.dto

import com.github.dankook_univ.auth.domain.Auth

interface AuthPort {
    fun save(auth: Auth): Auth
    fun getByEmail(email: String): Auth?
    fun getByUserId(userId: Long): Auth?
}
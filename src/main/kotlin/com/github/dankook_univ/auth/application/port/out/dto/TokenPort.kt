package com.github.dankook_univ.auth.application.port.out.dto

import com.github.dankook_univ.auth.domain.Token

interface TokenPort {
    fun getByAuthId(authId: Long): Token?
    fun save(authId: Long, token: Token, timeToLive: Long): Token
    fun delete(authId: Long)
}
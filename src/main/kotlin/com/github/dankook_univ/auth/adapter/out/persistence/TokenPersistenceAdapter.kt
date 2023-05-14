package com.github.dankook_univ.auth.adapter.out.persistence

import com.github.dankook_univ.auth.application.port.out.dto.TokenPort
import com.github.dankook_univ.auth.domain.Token
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class TokenPersistenceAdapter(
    redisTemplate: RedisTemplate<Any, Any>
) : TokenPort {
    private val operations: ValueOperations<Any, Any>

    init {
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(Token::class.java)
        operations = redisTemplate.opsForValue()
    }

    override fun getByAuthId(authId: Long): Token? {
        return operations.get(authId.toString()) as Token?
    }

    override fun save(authId: Long, token: Token, timeToLive: Long): Token {
        operations.set(authId.toString(), token, Duration.ofMillis(timeToLive))
        return token
    }

    override fun delete(authId: Long) {
        operations.getAndDelete(authId.toString())
    }
}
package com.github.dankook_univ.auth.adapter.`in`.web.filter

import com.github.dankook_univ.auth.application.port.`in`.TokenUseCase
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(
    private val tokenUseCase: TokenUseCase,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authentication = tokenUseCase.validation(resolveToken(request))
        if (authentication is Authentication) {
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7)
        }
        return null
    }
}
package com.github.dankook_univ.config

import com.github.dankook_univ.auth.adapter.`in`.web.filter.AuthFilter
import com.github.dankook_univ.auth.application.port.`in`.TokenUseCase
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class JwtSecurityConfig(
    private val tokenUseCase: TokenUseCase
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(
            AuthFilter(tokenUseCase),
            UsernamePasswordAuthenticationFilter::class.java
        )
    }
}
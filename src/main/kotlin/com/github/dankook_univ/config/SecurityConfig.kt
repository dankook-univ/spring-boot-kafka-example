package com.github.dankook_univ.config

import com.github.dankook_univ.auth.adapter.`in`.web.filter.JwtAccessDeniedHandler
import com.github.dankook_univ.auth.adapter.`in`.web.filter.JwtAuthenticationEntryPoint
import com.github.dankook_univ.auth.application.port.`in`.TokenUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenUseCase: TokenUseCase,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
) {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf().disable()

            .cors()
            .configurationSource(corsConfigurationSource())
            .and()

            .apply(JwtSecurityConfig(tokenUseCase))
            .and()

            .exceptionHandling()
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()

            .authorizeHttpRequests()
            .requestMatchers("/").permitAll()

            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/swagger-resources/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()

            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/auth/reissue").authenticated()
            .anyRequest().authenticated()
            .and()

            .build()
    }

    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}

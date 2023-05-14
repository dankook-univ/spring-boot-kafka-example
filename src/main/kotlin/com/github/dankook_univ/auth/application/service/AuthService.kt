package com.github.dankook_univ.auth.application.service

import com.github.dankook_univ.auth.application.port.`in`.AuthUseCase
import com.github.dankook_univ.auth.application.port.`in`.TokenUseCase
import com.github.dankook_univ.auth.application.port.`in`.dto.request.SignInRequest
import com.github.dankook_univ.auth.application.port.`in`.dto.request.SignUpRequest
import com.github.dankook_univ.auth.application.port.out.dto.AuthPort
import com.github.dankook_univ.auth.domain.Auth
import com.github.dankook_univ.auth.exception.*
import com.github.dankook_univ.user.application.port.`in`.dto.CreateUserRequest
import com.github.dankook_univ.user.application.port.out.UserUseCase
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val authPort: AuthPort,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val userUseCase: UserUseCase,
    private val tokenUseCase: TokenUseCase,
) : AuthUseCase {

    @Transactional
    override fun signIn(request: SignInRequest): Auth {
        val auth = authPort.getByEmail(request.email) ?: throw NotFoundAuthInfoWithEmailException(request.email)
        if (!passwordEncoder.matches(request.password, auth.password)) {
            throw WrongPasswordException(request.email)
        }

        auth.token = tokenUseCase.create(auth)

        return auth
    }

    @Transactional
    override fun signUp(request: SignUpRequest): Auth {
        if (authPort.getByEmail(request.email) != null) {
            throw DuplicatedEmailException(request.email)
        }

        if (userUseCase.getByNickname(request.nickname) != null) {
            throw DuplicatedNicknameException(request.nickname)
        }

        val user = userUseCase.create(
            CreateUserRequest(
                nickname = request.nickname,
            )
        )

        val auth = authPort.save(
            Auth(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                user = user,
            )
        )

        auth.token = tokenUseCase.create(auth)

        return auth
    }

    override fun signOut(userId: Long) {
        val auth = authPort.getByUserId(userId)
        if (auth is Auth) {
            tokenUseCase.delete(auth)
        }
    }

    @Transactional
    override fun reissue(refreshToken: String): Auth {
        if (tokenUseCase.validation(refreshToken) == null || !tokenUseCase.isRefreshToken(refreshToken)) {
            throw InvalidTokenException()
        }

        val auth = authPort.getByUserId(tokenUseCase.parseUserId(refreshToken)) ?: throw InvalidTokenException()

        auth.token = tokenUseCase.create(auth)

        return auth
    }
}
package com.github.dankook_univ.auth.application.service

import com.github.dankook_univ.auth.application.port.`in`.TokenUseCase
import com.github.dankook_univ.auth.application.port.`in`.dto.request.SignInRequest
import com.github.dankook_univ.auth.application.port.`in`.dto.request.SignUpRequest
import com.github.dankook_univ.auth.application.port.out.dto.TokenPort
import com.github.dankook_univ.auth.domain.Auth
import com.github.dankook_univ.auth.domain.Token
import com.github.dankook_univ.auth.exception.DuplicatedEmailException
import com.github.dankook_univ.auth.exception.DuplicatedNicknameException
import com.github.dankook_univ.auth.exception.WrongPasswordException
import com.github.dankook_univ.user.domain.User
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class AuthServiceTest(
    @Autowired private val authService: AuthService,
    @Autowired private val tokenUseCase: TokenUseCase,
    @Autowired private val tokenPort: TokenPort,
) {
    @Test
    @DisplayName("회원가입에 성공해요")
    fun signUp() {
        val auth = authService.signUp(
            SignUpRequest(
                email = "user@email.com",
                password = "password",
                nickname = "user"
            )
        )

        assertThat(auth).isNotNull.isInstanceOf(Auth::class.java)
        assertThat(auth.user).isNotNull.isInstanceOf(User::class.java)
        assertThat(auth.token).isNotNull.isInstanceOf(Token::class.java)

        assertThat(auth.email).isNotNull().isEqualTo("user@email.com")
        assertThat(auth.user.nickname).isNotNull().isEqualTo("user")

        assertThat(tokenPort.getByAuthId(auth.id!!)).isNotNull.isInstanceOf(Token::class.java)
    }

    @Test
    @DisplayName("회원가입에 실패해요. (중복된 이메일)")
    fun signUpFailedWithDuplicatedEmail() {
        authService.signUp(
            SignUpRequest(
                email = "user@email.com",
                password = "password",
                nickname = "userA"
            )
        )

        Assertions.assertThrows(DuplicatedEmailException::class.java) {
            authService.signUp(
                SignUpRequest(
                    email = "user@email.com",
                    password = "password",
                    nickname = "userB"
                )
            )
        }
    }

    @Test
    @DisplayName("회원가입에 실패해요. (중복된 닉네임)")
    fun signUpFailedWithDuplicatedNickname() {
        authService.signUp(
            SignUpRequest(
                email = "userB@email.com",
                password = "password",
                nickname = "user"
            )
        )

        Assertions.assertThrows(DuplicatedNicknameException::class.java) {
            authService.signUp(
                SignUpRequest(
                    email = "userA@email.com",
                    password = "password",
                    nickname = "user"
                )
            )
        }
    }

    @Test
    @DisplayName("로그인에 성공해요.")
    fun signIn() {
        val auth = authService.signUp(
            SignUpRequest(
                email = "user@email.com",
                password = "password",
                nickname = "user"
            )
        )

        val result = authService.signIn(
            SignInRequest(
                email = "user@email.com",
                password = "password"
            )
        )

        assertThat(result).isNotNull.isInstanceOf(Auth::class.java)
        assertThat(result.user).isNotNull.isInstanceOf(User::class.java)
        assertThat(result.token).isNotNull.isInstanceOf(Token::class.java)

        assertThat(result.email).isEqualTo(auth.email).isEqualTo("user@email.com")
        assertThat(result.user.id).isEqualTo(auth.user.id)

        assertThat(tokenPort.getByAuthId(auth.id!!)).isNotNull.isInstanceOf(Token::class.java)
    }

    @Test
    @DisplayName("로그인에 실패해요.")
    fun signInFailedWithWrongPassword() {
        authService.signUp(
            SignUpRequest(
                email = "user@email.com",
                password = "password",
                nickname = "user"
            )
        )

        Assertions.assertThrows(WrongPasswordException::class.java) {
            authService.signIn(
                SignInRequest(
                    email = "user@email.com",
                    password = "wrong_password"
                )
            )
        }
    }

    @Test
    @DisplayName("로그아웃에 성공해요.")
    fun signOut() {
        val auth = authService.signUp(
            SignUpRequest(
                email = "user@email.com",
                password = "password",
                nickname = "user"
            )
        )

        assertThat(tokenPort.getByAuthId(auth.id!!)).isNotNull.isInstanceOf(Token::class.java)

        tokenUseCase.validation(auth.token?.accessToken)?.name?.let { authService.signOut(it.toLong()) }

        assertThat(tokenPort.getByAuthId(auth.user.id!!)).isNull()
    }

    @Test
    @DisplayName("토큰 재발급에 성공해요.")
    fun reissue() {
        val auth = authService.signUp(
            SignUpRequest(
                email = "user@email.com",
                password = "password",
                nickname = "user"
            )
        )
        val token = auth.token

        Thread.sleep(1000)

        val reissuedAuth = auth.token?.refreshToken?.let { authService.reissue(it) }

        assertThat(reissuedAuth).isNotNull.isInstanceOf(Auth::class.java)

        assertThat(token?.accessToken).isNotNull.isNotEqualTo(reissuedAuth?.token?.accessToken)
        assertThat(token?.refreshToken).isNotNull.isNotEqualTo(reissuedAuth?.token?.refreshToken)
    }
}
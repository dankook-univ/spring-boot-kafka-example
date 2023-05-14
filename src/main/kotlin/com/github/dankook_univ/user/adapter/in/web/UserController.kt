package com.github.dankook_univ.user.adapter.`in`.web

import com.github.dankook_univ.user.application.port.out.UserUseCase
import org.springframework.stereotype.Controller

@Controller
class UserController(
    private val userUseCase: UserUseCase,
)
package com.github.dankook_univ.auth.exception

class WrongPasswordException(email: String) : IllegalArgumentException("[패스워드가 일치하지 않습니다] ($email)")
package com.github.dankook_univ.auth.exception

class DuplicatedEmailException(email: String) : IllegalArgumentException("[이메일 이미 존재합니다] ($email)")
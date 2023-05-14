package com.github.dankook_univ.auth.exception

class NotFoundAuthInfoWithEmailException(email: String) : IllegalArgumentException("[회원 정보를 찾을 수 업습니다] (email: $email)")
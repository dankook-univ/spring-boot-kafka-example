package com.github.dankook_univ.auth.exception

class DuplicatedNicknameException(nickname: String) : IllegalArgumentException("[닉네임이 이미 존재합니다] ($nickname)")
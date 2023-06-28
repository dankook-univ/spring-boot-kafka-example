package com.github.dankook_univ.producer.todo.adapter.`in`.web.request

import java.time.LocalDateTime

data class CreateTodoRequest(
    val title: String,
    val content: String,
    val deadline: LocalDateTime
)
package com.github.dankook_univ.todo.adapter.`in`.web.response

import com.github.dankook_univ.common.domain.Todo
import java.time.LocalDateTime

class TodoResponse(
    todo: Todo
) {
    val id: Long? = todo.id
    val createdAt: LocalDateTime = todo.createdAt
    val updatedAt: LocalDateTime = todo.updatedAt
    val title: String = todo.title
    val content: String = todo.content
    val checked: Boolean = todo.checked
    val deadline: LocalDateTime = todo.deadline
}
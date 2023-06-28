package com.github.dankook_univ.common.domain


import Core
import com.github.dankook_univ.todo.adapter.`in`.web.response.TodoResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class Todo(
    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val checked: Boolean,

    @Column(nullable = false)
    val deadline: LocalDateTime,

    ) : Core() {
    fun toResponse(): TodoResponse = TodoResponse(this)
}
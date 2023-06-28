package com.github.dankook_univ.consumer.todo.application.port.`in`

import com.github.dankook_univ.common.domain.Todo

interface ConsumerTodoUsecase {
    fun save(todo: Todo)
}
package com.github.dankook_univ.consumer.todo.application.port.out

import com.github.dankook_univ.common.domain.Todo

interface ConsumerSaveTodoPort {
    fun save(todo: Todo)
}
package com.github.dankook_univ.producer.todo.application.port.out

import com.github.dankook_univ.common.domain.Todo

interface ProducerSendTodoPort {
    fun sendTodo(todo: Todo)
}
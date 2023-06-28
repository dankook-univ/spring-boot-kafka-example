package com.github.dankook_univ.producer.todo.application.port.`in`

import com.github.dankook_univ.producer.todo.adapter.`in`.web.request.CreateTodoRequest

interface ProducerTodoUsecase {
    fun create(request: CreateTodoRequest)
}
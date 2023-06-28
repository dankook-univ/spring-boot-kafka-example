package com.github.dankook_univ.producer.todo.application.service

import com.github.dankook_univ.common.domain.Todo
import com.github.dankook_univ.producer.todo.adapter.`in`.web.request.CreateTodoRequest
import com.github.dankook_univ.producer.todo.application.port.`in`.ProducerTodoUsecase
import com.github.dankook_univ.producer.todo.application.port.out.ProducerSendTodoPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProducerTodoService(
    private val producerSendTodoPort: ProducerSendTodoPort
) : ProducerTodoUsecase {
    override fun create(request: CreateTodoRequest) {
        producerSendTodoPort.sendTodo(
            Todo(
                title = request.title,
                content = request.content,
                checked = false,
                deadline = request.deadline
            )
        )
    }

}
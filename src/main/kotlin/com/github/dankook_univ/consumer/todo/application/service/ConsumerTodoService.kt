package com.github.dankook_univ.consumer.todo.application.service

import com.github.dankook_univ.common.domain.Todo
import com.github.dankook_univ.consumer.todo.application.port.`in`.ConsumerTodoUsecase
import com.github.dankook_univ.consumer.todo.application.port.out.ConsumerSaveTodoPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConsumerTodoService(
    private val consumerSaveTodoPort: ConsumerSaveTodoPort
) : ConsumerTodoUsecase {
    
    @Transactional()
    override fun save(todo: Todo) {
        consumerSaveTodoPort.save(todo)
    }

}
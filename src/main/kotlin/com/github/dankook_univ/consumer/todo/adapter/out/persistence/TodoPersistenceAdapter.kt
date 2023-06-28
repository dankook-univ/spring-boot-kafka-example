package com.github.dankook_univ.consumer.todo.adapter.out.persistence

import com.github.dankook_univ.common.domain.Todo
import com.github.dankook_univ.consumer.todo.application.port.out.ConsumerSaveTodoPort
import org.springframework.stereotype.Repository

@Repository
class TodoPersistenceAdapter(
    private val todoRepository: TodoRepository
) : ConsumerSaveTodoPort {
    override fun save(todo: Todo) {
        todoRepository.save(todo)
    }
}
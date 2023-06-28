package com.github.dankook_univ.consumer.todo.adapter.out.persistence

import com.github.dankook_univ.common.domain.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {
    fun save(todo: Todo)
}
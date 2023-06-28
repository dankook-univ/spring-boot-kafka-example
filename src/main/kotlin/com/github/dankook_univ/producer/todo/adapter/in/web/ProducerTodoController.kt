package com.github.dankook_univ.producer.todo.adapter.`in`.web

import com.github.dankook_univ.producer.todo.adapter.`in`.web.request.CreateTodoRequest
import com.github.dankook_univ.producer.todo.application.port.`in`.ProducerTodoUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/todo")
class ProducerTodoController(
    private val producerTodoUsecase: ProducerTodoUsecase
) {

    @PostMapping("/new")
    fun create(@RequestBody request: CreateTodoRequest): ResponseEntity<String> {
        producerTodoUsecase.create(request)
        return ResponseEntity.ok().body("good")
    }

}
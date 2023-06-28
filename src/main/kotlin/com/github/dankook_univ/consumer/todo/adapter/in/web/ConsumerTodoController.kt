package com.github.dankook_univ.consumer.todo.adapter.`in`.web

import com.github.dankook_univ.common.domain.Todo
import com.github.dankook_univ.consumer.todo.application.port.`in`.ConsumerTodoUsecase
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/todo")
class ConsumerTodoController(

    @Value("\${spring.kafka.topic}")
    private val TOPIC: String,

    @Value("\${spring.kafka.consumer.group-id}")
    private val GROUP_ID: String,

    private val consumerTodoUsecase: ConsumerTodoUsecase
) {

    @KafkaListener(topics = ["todo"], groupId = "dankook")
    fun receive(todo: Todo) {
        consumerTodoUsecase.save(todo)
    }
}
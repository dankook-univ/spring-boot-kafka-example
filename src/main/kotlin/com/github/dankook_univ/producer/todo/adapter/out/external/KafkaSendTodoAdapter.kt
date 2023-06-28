package com.github.dankook_univ.producer.todo.adapter.out.external

import com.github.dankook_univ.common.domain.Todo
import com.github.dankook_univ.config.logger
import com.github.dankook_univ.producer.todo.application.port.out.ProducerSendTodoPort
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class KafkaSendTodoAdapter(
    @Value("\${spring.kafka.topic}")
    private val TOPIC: String,

    private val kafkaTemplate: KafkaTemplate<String, Todo>,

    ) : ProducerSendTodoPort {

    private final val log: Logger = logger<KafkaSendTodoAdapter>()

    override fun sendTodo(todo: Todo) {
        // kafka producer
        val future: CompletableFuture<SendResult<String, Todo>> = kafkaTemplate.send(TOPIC, todo)

        // kafka add callback
//        future.thenAccept { result ->
//            log.info(
//                "Callback partition:{}, offset:{}",
//                result.recordMetadata.partition(),
//                result.recordMetadata.offset()
//            )
//        }
        future.whenComplete { result, ex ->
            if (ex == null) {
                log.info(
                    "Message 전달 성공 Callback partition:{}, offset:{}",
                    result.recordMetadata.partition(),
                    result.recordMetadata.offset()
                )
            } else {
                log.info(
                    "Message 전달 오류 {},",
                    ex.message
                )

            }
        }
    }

}
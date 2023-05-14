package com.github.dankook_univ

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class SpringBootKafkaExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringBootKafkaExampleApplication>(*args)
}

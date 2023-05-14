package com.github.dankook_univ.config

import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.junit.jupiter.Container
import java.io.File

@Configuration
class TestContainer {
    companion object {
        @JvmStatic
        @Container
        private val container = DockerComposeContainer<Nothing>(
            File("src/test/resources/docker-compose.yml")
        )
    }

    init {
        container.start()
    }
}
package com.batch.pratice.springbatchpratice

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class SpringBatchPracticeApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchPracticeApplication>(*args)
}

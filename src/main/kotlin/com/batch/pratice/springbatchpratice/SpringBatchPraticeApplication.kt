package com.batch.pratice.springbatchpratice

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class SpringBatchPraticeApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchPraticeApplication>(*args)
}

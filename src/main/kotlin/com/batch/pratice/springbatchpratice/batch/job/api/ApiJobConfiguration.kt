package com.batch.pratice.springbatchpratice.batch.job.api

import com.batch.pratice.springbatchpratice.batch.tasklet.ApiEndTasklet
import com.batch.pratice.springbatchpratice.batch.tasklet.ApiStartTasklet
import javax.batch.api.listener.JobListener
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Configuration

@Configuration
class ApiJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val apiStartTasklet: ApiStartTasklet,
    private val apiEndTasklet: ApiEndTasklet,
    private val jobStep: Step
) {

    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    fun apiJob(): Job {
        return jobBuilderFactory.get("apiJob")
            .listener(JobListener())
            .start(apiStep1())
            .next(jobStep)
            .next(apiStep2())
            .build()
    }

    fun apiStep1(): Step {
        return stepBuilderFactory.get("apiStep1")
            .tasklet(apiStartTasklet)
            .build()
    }

    fun apiStep2(): Step {
        return stepBuilderFactory.get("apiStep2")
            .tasklet(apiEndTasklet)
            .build()
    }
}
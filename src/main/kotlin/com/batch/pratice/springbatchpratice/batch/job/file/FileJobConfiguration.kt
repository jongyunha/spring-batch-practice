package com.batch.pratice.springbatchpratice.batch.job.file

import com.batch.pratice.springbatchpratice.batch.chunk.processor.FileItemProcessor
import com.batch.pratice.springbatchpratice.domain.Product
import com.batch.pratice.springbatchpratice.domain.ProductVO
import javax.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FileJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val entityManagerFactory: EntityManagerFactory
) {

    @Bean
    fun fileJob(): Job {
        return jobBuilderFactory.get("fileJob")
            .start(fileStep())
            .build()
    }

    @Bean
    fun fileStep(): Step {
        return stepBuilderFactory.get("fileStep")
            .chunk<ProductVO, Product>(10)
            .reader(fileItemReader(null))
            .processor(fileItemProcessor())
            .writer(fileItemWriter())
            .build()
    }

    @Bean
    @StepScope
    fun fileItemReader(
        @Value("#{jobParameters['requestDate']}")
        requestDate: String?
    ): FlatFileItemReader<ProductVO> {
        return FlatFileItemReaderBuilder<ProductVO>()
            .name("flatFile")
            .resource(ClassPathResource("product_${requestDate}.csv"))
            .fieldSetMapper { fieldSet ->
                ProductVO(
                    id = fieldSet.readLong("id"),
                    name = fieldSet.readString("name"),
                    price = fieldSet.readInt("price"),
                    type = fieldSet.readString("type")
                )
            }
            .linesToSkip(1)
            .delimited()
            .names("id", "name", "price", "type")
            .build()
    }

    @Bean
    fun fileItemProcessor(): ItemProcessor<ProductVO, Product> {
        return FileItemProcessor()
    }

    @Bean
    fun fileItemWriter(): ItemWriter<Product> {
        return JpaItemWriterBuilder<Product>()
            .entityManagerFactory(entityManagerFactory)
            .usePersist(true)
            .build()
    }

}
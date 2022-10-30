package com.batch.pratice.springbatchpratice.batch.job.api

import com.batch.pratice.springbatchpratice.batch.partition.ProductPartitioner
import com.batch.pratice.springbatchpratice.domain.ProductVO
import javax.sql.DataSource
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper

@Configuration
class ApiStepConfiguration(
    private val stepBuilderFactory: StepBuilderFactory,
    private val dataSource: DataSource,
) {

    private val chunkSize = 10;

    @Bean
    fun apiMasterStep(): Step {
        return stepBuilderFactory.get("apiMasterStep")
            .partitioner(apiSlaveStep().name.partitioner)
            .step(apiSlaveStep())
            .gridSize(3)
            .taskExecutor(taskExecutor())
            .build()
    }

    @Bean
    fun apiSlaveStep(): Step {
        return stepBuilderFactory.get("apiSlaveStep")
            .chunk<ProductVO, ProductVO>(chunkSize)
            .reader(itemReader(null))
            .processor(itemProcessor())
            .writer(itemWriter())
            .build()
    }

    @Bean
    fun partitioner(): ProductPartitioner {
        return ProductPartitioner(dataSource)
    }

    @Bean
    @StepScope
    fun itemReader(
        @Value("#{stepExecutionContext['product']}")
        productVO: ProductVO?
    ): ItemReader<ProductVO> {
        val reader = JdbcPagingItemReader<ProductVO>();

        reader.setDataSource(dataSource)
        reader.pageSize = chunkSize
        reader.setRowMapper(BeanPropertyRowMapper(ProductVO::class.java))

        val queryProvider = MySqlPagingQueryProvider()
        queryProvider.setSelectClause("id, name, price, type")
        queryProvider.setFromClause("from product")
        queryProvider.setWhereClause("where type = :type")

        val sortKeys = HashMap<String, Order>(1)
        sortKeys["id"] = Order.DESCENDING
        queryProvider.sortKeys = sortKeys

        reader.setParameterValues(getParameterForQuery("type", productVO!!.type!!))
        reader.setQueryProvider(queryProvider)
        reader.afterPropertiesSet()

        return reader
    }
}
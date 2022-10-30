package com.batch.pratice.springbatchpratice.batch.partition

import javax.sql.DataSource
import org.springframework.batch.core.partition.support.Partitioner
import org.springframework.batch.item.ExecutionContext

class ProductPartitioner(
    private val dataSource: DataSource
) : Partitioner {

    override fun partition(gridSize: Int): MutableMap<String, ExecutionContext> {
        TODO("Not yet implemented")
    }
}
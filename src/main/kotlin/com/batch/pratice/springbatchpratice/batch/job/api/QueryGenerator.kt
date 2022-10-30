package com.batch.pratice.springbatchpratice.batch.job.api

import com.batch.pratice.springbatchpratice.batch.rowmapper.ProductRowMapper
import com.batch.pratice.springbatchpratice.domain.ProductVO
import java.sql.ResultSet
import javax.sql.DataSource
import org.springframework.jdbc.core.JdbcTemplate

fun getProductList(dataSource: DataSource): List<ProductVO> {
    val jdbcTemplate = JdbcTemplate(dataSource)
    val productList = jdbcTemplate.query("select type from product group by type", object : ProductRowMapper() {
        override fun mapRow(rs: ResultSet, rowNum: Int): ProductVO {
            return ProductVO(type = rs.getString("type"))
        }
    })

    return productList
}

fun getParameterForQuery(parameter: String, value: String): Map<String, Any> {
    val parameters = HashMap<String, Any>()
    parameters[parameter] = value
    return parameters
}
package com.batch.pratice.springbatchpratice.batch.rowmapper

import com.batch.pratice.springbatchpratice.domain.ProductVO
import java.sql.ResultSet
import org.springframework.jdbc.core.RowMapper

open class ProductRowMapper : RowMapper<ProductVO> {
    override fun mapRow(rs: ResultSet, rowNum: Int): ProductVO {
        return ProductVO(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            price = rs.getInt("price"),
            type = rs.getString("type")
        )
    }
}
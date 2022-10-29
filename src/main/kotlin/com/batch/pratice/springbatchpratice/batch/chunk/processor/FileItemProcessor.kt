package com.batch.pratice.springbatchpratice.batch.chunk.processor

import com.batch.pratice.springbatchpratice.domain.Product
import com.batch.pratice.springbatchpratice.domain.ProductVO
import org.springframework.batch.item.ItemProcessor

class FileItemProcessor : ItemProcessor<ProductVO, Product> {
    override fun process(item: ProductVO): Product {
        return Product(
            id = item.id,
            name = item.name,
            price = item.price,
            type = item.type
        )
    }
}
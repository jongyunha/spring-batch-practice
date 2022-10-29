package com.batch.pratice.springbatchpratice.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Product(
    id: Long,
    var name: String,
    var price: Int,
    var type: String,
) {
    @Id
    var id: Long? = id
}
package com.yuan.online.model.query

data class ProductListQuery(
    var keyword:String,
    var categoryIds:ArrayList<Int>
)

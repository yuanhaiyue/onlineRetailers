package com.yuan.online.model.vo

import java.sql.Date

data class OrderItemVo(
    val id:Int?=null,
    val orderNo:String?=null,
    val productId:Int?=null,
    val productName:String?=null,
    val productImg:String?=null,
    val unitPrice:Int?=null,
    val quantity:Int?=null,
    val totalPrice:Int?=null
)

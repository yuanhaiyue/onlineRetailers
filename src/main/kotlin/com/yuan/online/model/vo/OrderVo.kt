package com.yuan.online.model.vo


import java.sql.Date

data class OrderVo(
    var orderNo:String?=null,
    var userId:Int?=null,
    var totalPrice:Int?=null,
    var receiverName:String?=null,
    var receiverMobile:String?=null,
    var receiverAddress:String?=null,
    var orderStatus:Int?=null,
    var postage:Int?=null,
    var paymentType:Int?=null,
    var deliverTime:Date?=null,
    var payTime:Date?=null,
    var endTime:Date?=null,
    var createTime:Date?=null,
    var updateTime:Date?=null,
    var orderStatusName:String?=null,
    var orderItemList:List<OrderItemVo>?=null
)

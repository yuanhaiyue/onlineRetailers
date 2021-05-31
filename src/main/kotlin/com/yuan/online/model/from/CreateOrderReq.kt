package com.yuan.online.model.from

data class CreateOrderReq(
    val receiverName:String,
    val receiverMobile:String,
    val receiverAddress:String,
    val postage:Int=0,
    val paymentType:Int=1
)

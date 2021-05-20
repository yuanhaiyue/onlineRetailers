package com.yuan.online.model.vo


/**
 * 描述   CartVo,给前端展示用
 */

data class CartVo(
    var id:Int,
    var productId:Int,
    var userId:Int,
    var quantity:Int,
    var selected:Int,
    var price:Int,
    var totalPrice:Int,
    var productName:String,
    var productImage:String
)
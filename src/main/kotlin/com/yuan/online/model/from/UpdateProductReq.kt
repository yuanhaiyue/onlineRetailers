package com.yuan.online.model.from

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull


data class UpdateProductReq (
    @NotNull(message = "商品id不能为空")
    var id:Int?=null,

    var name:String?=null,
    //max 500
    var image:String?=null,
    //max 500
    var detail:String?=null,

    var categoryId:Int?=null,

    @Min(1,message = "价格不能小于1")
    var price:Int?=null,


)
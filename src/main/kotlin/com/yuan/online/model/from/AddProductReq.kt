package com.yuan.online.model.from

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.sql.Timestamp
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull


data class AddProductReq (

    @NotNull(message = "商品名称不能为null")
    var name:String?=null,
    //max 500

    @NotNull(message = "商品图片不能为null")
    var image:String?=null,
    //max 500
    var detail:String?=null,

    @NotNull(message = "商品分类不能为null")
    var categoryId:Int?=null,

    @NotNull(message = "商品价格不能为null")
    @Min(1,message = "价格不能小于1")
    var price:Int?=null,

    @NotNull(message = "商品库存不能为null")
    @Max(10000, message = "库存不能大于一万")
    var stock:Int?=null,

    var status:Int?=null,

)
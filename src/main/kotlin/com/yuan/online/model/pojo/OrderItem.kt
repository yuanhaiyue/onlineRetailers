package com.yuan.online.model.pojo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.sql.Timestamp

@Data
@TableName(value = "imooc_mall_order_item")
class OrderItem {
    @TableId(value = "id")
    var id:Int?=null

    //max 128
    var orderNo:String?=null

    var productId:Int?=null
    //max 100
    var productName:String?=null
    //max 128
    var productImg:String?=null

    var unitPrice:Int?=null

    var quantity:Int?=null

    var totalPrice:Int?=null

    var createTime:Timestamp?=null

    var updateTime:Timestamp?=null
}
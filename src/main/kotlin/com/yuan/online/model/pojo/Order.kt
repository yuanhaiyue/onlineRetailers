package com.yuan.online.model.pojo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.sql.Timestamp

@Data
@TableName(value = "imooc_mall_order")
class Order {

    @TableId(value = "id")
    var id:Int?=null

    var orderNo:String?=null

    var userId:Int?=null

    var totalPrice:Int?=null
    //max 32
    var receiverName:String?=null
    //max 32
    var receiverMobile:String?=null
    //max 128
    var receiverAddress:String?=null

    var orderStatus:Int?=null

    var postage:Int?=null

    var paymentType:Int?=null

    var deliveryTime:Timestamp?=null

    var payTime:Timestamp?=null

    var endTime:Timestamp?=null

    var createTime:Timestamp?=null

    var updateTime:Timestamp?=null



}
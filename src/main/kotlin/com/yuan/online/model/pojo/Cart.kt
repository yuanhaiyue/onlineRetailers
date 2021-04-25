package com.yuan.online.model.pojo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.sql.Timestamp


@TableName(value = "imooc_mal_cart")
@Data
class Cart {
    @TableId(value = "id")
    var id:Int?=null

    var productId:Int?=null

    var userId:Int?=null

    var quantity:Int?=null

    var selected:Int?=null

    var createTime:Timestamp?=null

    var updateTime:Timestamp?=null
}
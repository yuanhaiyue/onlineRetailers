package com.yuan.online.model.pojo

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.sql.Timestamp

@Data
@TableName(value = "imooc_mall_product")
class Product {

    @TableId(value = "id",type = IdType.AUTO)
    var id:Int?=null
    //max 100
    var name:String?=null
    //max 500
    var image:String?=null
    //max 500
    var detail:String?=null

    var categoryId:Int?=null

    var price:Int?=null

    var stock:Int?=null

    var status:Int?=null

    var createTime:Timestamp?=null

    var updateTime:Timestamp?=null
}
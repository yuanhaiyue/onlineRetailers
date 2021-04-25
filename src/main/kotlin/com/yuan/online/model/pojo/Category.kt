package com.yuan.online.model.pojo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.sql.Timestamp


@Data

@TableName(value = "imooc_mall_category")
class Category {
    @TableId(value = "id")
    var id:Int?=null

    var name:String?=null

    var type:Int?=null

    var parentId:Int?=null

    var orderNum:Int?=null

    var createTime:Timestamp?=null

    var updateTime:Timestamp?=null


}
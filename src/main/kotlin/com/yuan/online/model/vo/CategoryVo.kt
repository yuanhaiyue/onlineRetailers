package com.yuan.online.model.vo

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.io.Serializable
import java.sql.Timestamp


@Data
class CategoryVo :Serializable{

    var id:Int?=null

    var name:String?=null

    var type:Int?=null

    var parentId:Int?=null

    var orderNum:Int?=null

    var createTime:Timestamp?=null

    var updateTime:Timestamp?=null

    var childCategory:ArrayList<CategoryVo> = ArrayList()
}
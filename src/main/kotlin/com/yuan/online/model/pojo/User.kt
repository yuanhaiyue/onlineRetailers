package com.yuan.online.model.pojo

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.sql.Timestamp

@Data
@TableName(value = "imooc_mall_user")
class User {
    @TableId(value = "id",type = IdType.AUTO)
    var id:Int?=null
    //max 32
    var username:String?=null
    //max 50
    var password:String?=null
    //max 50
    var personalizedSignature:String?=null

    var role:Int?=null

    var createTime:Timestamp?=null

    var updateTime:Timestamp?=null
}
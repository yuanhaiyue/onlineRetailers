package com.yuan.online.model.from

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.sql.Timestamp
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull


data class ProductListReq (
    var keyword:String?=null,

    var categoryId:Int?=null,

    var orderBy:String?=null,

    var pageNum:Int?=1,

    var pageSize:Int?=10

)
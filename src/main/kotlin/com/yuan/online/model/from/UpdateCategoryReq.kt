package com.yuan.online.model.from

import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UpdateCategoryReq(@NotNull(message = "id不能为空")val id:Int,
                             @Size(min = 2,max = 5,message = "name需要在2-5之间")val name:String,
                             @Max(3) val type:Int?=null,
                             val parentId:Int?=null,
                             val orderNum:Int?=null)

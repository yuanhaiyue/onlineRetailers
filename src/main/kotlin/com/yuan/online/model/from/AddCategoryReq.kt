package com.yuan.online.model.from

import lombok.Data
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Data
data class AddCategoryReq(@Size(min = 2,max = 5,message = "name长度需在2-5之间") @NotNull(message = "name不能为空") val name:String?,
                          @NotNull(message = "type不能为空") @Max(3,message = "type长度不能大于3") val type:Int?,
                          @NotNull(message = "parentId不能为空") val parentId:Int?,
                          @NotNull(message = "orderNum不能为空") val orderNum:Int?)

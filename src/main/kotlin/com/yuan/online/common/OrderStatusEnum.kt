package com.yuan.online.common

import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import lombok.Getter

@Getter
enum class OrderStatusEnum(var code:Int,var value:String) {
    CANCELED(0,"用户已取消"),
    NOT_PAID(10,"未付款"),
    PAID(20,"已付款"),
    DELIVERED(30,"已发货"),
    FINISHED(40,"交易完成");
    companion object{
        @JvmStatic
        fun codeOf(code:Int?):OrderStatusEnum{
            for (orderStatusEnum in values()){
                if (orderStatusEnum.code==code){
                    return orderStatusEnum
                }
            }
            throw MallExceptionT(MallExceptionEnum.NO_ENUM)
        }
    }



}
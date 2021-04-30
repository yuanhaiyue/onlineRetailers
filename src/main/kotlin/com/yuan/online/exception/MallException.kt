package com.yuan.online.exception


import lombok.Getter

@Getter
class MallException( var code:Int, var messages: String) :RuntimeException(){
    constructor(ex:MallExceptionEnum):this(ex.code,ex.msg){

    }


}
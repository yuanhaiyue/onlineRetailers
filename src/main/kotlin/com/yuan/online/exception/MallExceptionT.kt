package com.yuan.online.exception

data class MallExceptionT(var code:Int, override var message: String):Exception(){
    constructor(ex:MallExceptionEnum):this(ex.code,ex.msg){}
}

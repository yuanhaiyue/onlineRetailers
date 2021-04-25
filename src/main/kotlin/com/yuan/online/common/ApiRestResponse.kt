package com.yuan.online.common

import com.yuan.online.exception.MallExceptionEnum
import lombok.Data

@Data
class ApiRestResponse {
    private var status:Int?=null

    private var msg:String?=null

    var data:Any?=null

    companion object{
        @JvmStatic
        private val OK_CODE:Int=10000
        @JvmStatic
        private val OK_MSG:String="SUCCESS"

        @JvmStatic
        fun success(): ApiRestResponse {
            return ApiRestResponse()
        }

        @JvmStatic
        fun success(result:Any): ApiRestResponse {
            val resp= ApiRestResponse()
            resp.data=result
            return resp
        }
        @JvmStatic
        fun error(code:Int,msg:String):ApiRestResponse{
            return ApiRestResponse(code,msg)
        }
        @JvmStatic
        fun error(ex:MallExceptionEnum):ApiRestResponse{
            return ApiRestResponse(ex.code,ex.msg)
        }


    }
    constructor(status:Int,msg:String,data:Any){
        this.data=data
        this.msg=msg
        this.status=status
    }
    constructor(status: Int,msg: String){
        this.status=status
        this.msg=msg
    }
    constructor():this(OK_CODE, OK_MSG){

    }

    override fun toString(): String {
        return "ApiRestResponse(status=$status, msg=$msg, data=$data)"
    }


}
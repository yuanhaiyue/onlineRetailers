package com.yuan.online.common

import com.yuan.online.exception.MallExceptionEnum

data class ApiResponse(var status:Int?=null,var msg:String?=null,var data:Any?=null){
    companion object{
        @JvmStatic
        private val OK_CODE:Int=10000
        @JvmStatic
        private val OK_MSG:String="SUCCESS"

        @JvmStatic
        fun success(): ApiResponse {
            return ApiResponse()
        }

        @JvmStatic
        fun success(result:Any): ApiResponse {
            val resp= ApiResponse()
            resp.data=result
            return resp
        }
        @JvmStatic
        fun error(code:Int,msg:String):ApiResponse{
            return ApiResponse(code,msg)
        }
        @JvmStatic
        fun error(ex: MallExceptionEnum):ApiResponse{
            return ApiResponse(ex.code,ex.msg)
        }


    }
    constructor(status: Int,msg: String) :this(status,msg,null) {

    }
    constructor():this(OK_CODE, OK_MSG){

    }


    override fun toString(): String {
        return "ApiRestResponse(status=$status, msg=$msg, data=$data)"
    }

}

package com.yuan.online.exception

import com.yuan.online.common.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody


@ControllerAdvice
class GlobalExceptionHandler {
    private val log: Logger =LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(e:Exception):Any{
        return ApiResponse.error(MallExceptionEnum.SYSTEM_ERROR)
    }

    @ExceptionHandler(MallExceptionT::class)
    @ResponseBody
    fun handleMallException(e:MallExceptionT):Any{
        log.error("MallException: $e")
        return ApiResponse.error(e.code,e.message)
    }
}
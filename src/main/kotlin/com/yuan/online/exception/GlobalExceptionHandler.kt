package com.yuan.online.exception

import com.yuan.online.common.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody


//@RestControllerAdvice
@ControllerAdvice
class GlobalExceptionHandler {
    private val log: Logger =LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

//    @ExceptionHandler(Exception::class)
//    @ResponseBody
//    fun handleException(e:Exception):Any{
//        return ApiResponse.error(MallExceptionEnum.SYSTEM_ERROR)
//    }

    @ExceptionHandler(MallExceptionT::class)
    @ResponseBody
    fun handleMallException(e:MallExceptionT):Any{
        log.error("MallException: $e")
        return ApiResponse.error(e.code,e.message)
    }
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleMethodArgumentNotValidException(e:MethodArgumentNotValidException):ApiResponse{
        log.error("MethodArgumentNotValidException: ",e)
        return handleBindingResult(e.bindingResult)
    }

    private fun handleBindingResult(result:BindingResult):ApiResponse{
        //把异常处理为 对外暴露的提示
        val list = ArrayList<String>()
        if (result.hasErrors()){
            val allErrors:List<ObjectError> = result.allErrors
            allErrors.forEach {
                list.add(it.defaultMessage.toString())
            }
        }
        if (list.size==0){
            return ApiResponse.error(MallExceptionEnum.REQUEST_PARAM_ERROR)
        }

        return ApiResponse.error(MallExceptionEnum.REQUEST_PARAM_ERROR.code,list.toString())
    }
}
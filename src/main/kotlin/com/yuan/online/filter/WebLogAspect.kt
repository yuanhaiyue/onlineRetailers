package com.yuan.online.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@Aspect
@Component
class WebLogAspect {

    private val logger=LoggerFactory.getLogger(WebLogAspect::class.java)

    @Pointcut("execution(public * com.yuan.online.controller.*.*(..))")
    fun webLog(){

    }
    @Before("webLog()")
    fun doBefore(joinPoint:JoinPoint){
        val attributes:ServletRequestAttributes?= RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val req:HttpServletRequest?=attributes?.request

        logger.info("URL :${req?.requestURL.toString()}")
        logger.info("HTTP_METHOD :${req?.method}")
        logger.info("IP : ${req?.remoteAddr}")
        logger.info(joinPoint.signature.declaringTypeName+"."+joinPoint.signature.name)
        logger.info("ARGS : ${Arrays.toString(joinPoint.args)}")
    }
    @AfterReturning(returning = "res",pointcut = "webLog()")
    fun doAfterReturning(res:Any){
        logger.info("RESPONSE : "+ObjectMapper().writeValueAsString(res))
    }


}
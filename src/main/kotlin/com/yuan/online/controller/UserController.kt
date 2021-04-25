package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.common.ApiRestResponse
import com.yuan.online.common.Constant
import com.yuan.online.exception.MallException
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.from.UserLoginParam
import com.yuan.online.model.pojo.User
import com.yuan.online.service.UserService
import com.yuan.online.service.impl.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession
import kotlin.jvm.Throws

/**
 * 用户控制器
 */
@RestController
class UserController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/user")
    fun getUser():User?{
        return userService.getUser()
    }
    @Throws
    @PostMapping("/register")
    fun register(@RequestParam("userName") userName:String, @RequestParam("password")password:String):ApiResponse{
        if (!StringUtils.hasLength(userName)){
            return ApiResponse.error(MallExceptionEnum.NEED_USER_NAME)
        }
        if (!StringUtils.hasLength(password)){
            return ApiResponse.error(MallExceptionEnum.NEED_PASSWORD)
        }
        if (password.length<8){
            return ApiResponse.error(MallExceptionEnum.PASSWORD_TOO_SHORT)
        }
        userService.register(userName,password)
        return ApiResponse.success()
    }
    @PostMapping("/login")
    fun login(@RequestBody param:UserLoginParam,session:HttpSession):ApiResponse{
        if (!StringUtils.hasLength(param.userName)){
            return ApiResponse.error(MallExceptionEnum.NEED_USER_NAME)
        }
        if (!StringUtils.hasLength(param.password)){
            return ApiResponse.error(MallExceptionEnum.NEED_PASSWORD)
        }
        val user:User=userService.login(param)
        user.password=null
        session.setAttribute(Constant.MALL_USER,user)
        return ApiResponse.success(user)
    }
}
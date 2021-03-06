package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.common.Constant
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.model.from.UserLoginParam
import com.yuan.online.model.pojo.User
import com.yuan.online.service.UserService
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

    /**
     * 用户注册接口
     *
     */
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

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    fun login(@RequestBody param:UserLoginParam, session:HttpSession):ApiResponse{
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

    /**
     * 信息更新接口
     */
    @PostMapping("/user/update")
    fun updateUserInfo(session: HttpSession,@RequestParam signature:String):ApiResponse{
        val user: User? = session.getAttribute(Constant.MALL_USER) as User?
        if (user==null){
            return ApiResponse.error(MallExceptionEnum.NEED_LOGIN)
        }
        val curUser= User()
        curUser.id=user.id
        curUser.personalizedSignature=signature
        userService.updateInformation(user)
        return ApiResponse.success()
    }
    @PostMapping("/user/logout")
    fun logout(session: HttpSession):ApiResponse{
        session.removeAttribute(Constant.MALL_USER)
        return ApiResponse.success()
    }

    /**
     * 管理员登录接口
     * @param param 用户登录参数对象
     * @param session
     * @throws MallException
     */


    @PostMapping("/adminLogin")
    fun adminLogin(@RequestBody param:UserLoginParam, session:HttpSession):ApiResponse{
        if (!StringUtils.hasLength(param.userName)){
            return ApiResponse.error(MallExceptionEnum.NEED_USER_NAME)
        }
        if (!StringUtils.hasLength(param.password)){
            return ApiResponse.error(MallExceptionEnum.NEED_PASSWORD)
        }
        val user:User=userService.login(param)
        if (userService.checkAdminRole(user)){
            user.password=null
            session.setAttribute(Constant.MALL_USER,user)
            return ApiResponse.success(user)
        }else{
            return ApiResponse.error(MallExceptionEnum.NEED_ADMIN)
        }



    }

}
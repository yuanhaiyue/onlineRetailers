package com.yuan.online.service

import com.yuan.online.model.from.UserLoginParam
import com.yuan.online.model.pojo.User
import javax.servlet.http.HttpSession

interface UserService {
    fun getUser():User?

    fun register(username:String,password:String)

    fun login(loginParam:UserLoginParam):User
}
package com.yuan.online.service

import com.yuan.online.model.from.UserLoginParam
import com.yuan.online.model.pojo.User


interface UserService {
    fun getUser(): User?

    fun register(username:String,password:String)

    fun login(loginParam: UserLoginParam): User

    fun updateInformation(user: User)
    fun checkAdminRole(user: User): Boolean
}

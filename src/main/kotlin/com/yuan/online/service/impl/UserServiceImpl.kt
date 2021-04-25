package com.yuan.online.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.dao.UserMapper
import com.yuan.online.model.from.UserLoginParam
import com.yuan.online.model.pojo.User
import com.yuan.online.service.UserService
import com.yuan.online.util.MD5Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession


@Service
class UserServiceImpl:UserService{
    @Autowired
    private lateinit var userMapper:UserMapper


    override fun getUser(): User? {
        return userMapper.selectById(1)
    }
    @Throws(MallExceptionT::class)
    override fun register(username: String, password: String) {
        val query=QueryWrapper<User>()
        query.eq("username",username)
        val result:User?=userMapper.selectOne(query)
        if (result!=null){
            throw MallExceptionT(MallExceptionEnum.NAME_EXISTED)
        }
        val user=User()
        user.username=username
        user.password=MD5Utils.getMD5Str(password)
        val cnt:Int=userMapper.insert(user)
        if (cnt==0){
            throw MallExceptionT(MallExceptionEnum.INSERT_FAILED)
        }

    }

    override fun login(loginParam: UserLoginParam): User {
        val md5Password: String = MD5Utils.getMD5Str(loginParam.password)
        val queryWrapper = QueryWrapper<User>()
        queryWrapper.eq("name", loginParam.userName).eq("password", md5Password)
        return userMapper.selectOne(queryWrapper) ?: throw MallExceptionT(MallExceptionEnum.WRONG_PASSWORD)
    }
}
package com.yuan.online.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.dao.UserMapper
import com.yuan.online.model.from.UserLoginParam
import com.yuan.online.model.pojo.User
import com.yuan.online.service.UserService

import com.yuan.online.util.MD5Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service



@Service
class UserServiceImpl: UserService {
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
        queryWrapper.eq("username", loginParam.userName).eq("password", md5Password)
        return userMapper.selectOne(queryWrapper) ?: throw MallExceptionT(MallExceptionEnum.WRONG_PASSWORD)
    }

    override fun updateInformation(user: User) {
        //更新个性签名
        val cnt:Int=userMapper.update(user, null)
        if (cnt!=1){
            throw MallExceptionT(MallExceptionEnum.UPDATE_FAILED)
        }
    }

    override fun checkAdminRole(user: User):Boolean{
        //1:普通用户 2:管理用户
        return user.role== 2
    }

}
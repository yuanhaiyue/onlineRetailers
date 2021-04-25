package com.yuan.online.model.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yuan.online.model.pojo.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper:BaseMapper<User> {
}
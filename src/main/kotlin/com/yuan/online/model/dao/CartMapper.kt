package com.yuan.online.model.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yuan.online.model.pojo.Cart
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CartMapper :BaseMapper<Cart>{
}
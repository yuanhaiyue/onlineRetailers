package com.yuan.online.model.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yuan.online.model.pojo.Cart
import com.yuan.online.model.vo.CartVo
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface CartMapper :BaseMapper<Cart>{

    fun selectList(@Param ("userId") userId:Int):List<CartVo>
}
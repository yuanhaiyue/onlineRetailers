package com.yuan.online.model.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yuan.online.model.pojo.Product

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface ProductMapper:BaseMapper<Product> {

    @Select("select from imooc_mall_product where name=#{name}")
    fun selectByName(@Param("name") name:String):Product?
}
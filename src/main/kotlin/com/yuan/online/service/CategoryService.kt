package com.yuan.online.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.yuan.online.model.from.AddCategoryReq
import com.yuan.online.model.from.UpdateCategoryReq
import com.yuan.online.model.pojo.Category
import com.yuan.online.model.vo.CategoryVo

interface CategoryService {


    fun add(addCategoryReq: AddCategoryReq)

    fun update(updateCategoryReq: Category)

    fun delete(id: Int)

    fun listForAdmin(pageNum: Long, pageSize: Long): IPage<Category>

    fun listCategoryForCustomer(): List<CategoryVo>


}
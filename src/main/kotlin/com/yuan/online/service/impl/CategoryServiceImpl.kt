package com.yuan.online.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.dao.CategoryMapper
import com.yuan.online.model.from.AddCategoryReq
import com.yuan.online.model.pojo.Category
import com.yuan.online.model.vo.CategoryVo
import com.yuan.online.service.CategoryService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils


@Service
class CategoryServiceImpl :CategoryService{
    @Autowired
    lateinit var categoryMapper:CategoryMapper

    override fun add(addCategoryReq: AddCategoryReq){
        val category=Category()
        BeanUtils.copyProperties(addCategoryReq,category)
        val query=QueryWrapper<Category>()
        query.eq("name",category.name)
        val categoryOld=categoryMapper.selectOne(query)
        if (categoryOld!=null){
            throw MallExceptionT(MallExceptionEnum.NAME_EXISTED)
        }
        val cnt:Int=categoryMapper.insert(category)
        if (cnt==0){
            throw MallExceptionT(MallExceptionEnum.CREATE_FAILED)
        }
    }

    override fun update(updateCategoryReq: Category){
        if (!StringUtils.hasLength(updateCategoryReq.name)){
            val query=QueryWrapper<Category>()
            query.eq("name",updateCategoryReq.name)
            val category=categoryMapper.selectOne(query)
            if (category!=null && category.id != updateCategoryReq.id){
                throw MallExceptionT(MallExceptionEnum.NAME_EXISTED)
            }
        }
        val cnt:Int=categoryMapper.updateById(updateCategoryReq)
        if (cnt==0){
            throw MallExceptionT(MallExceptionEnum.UPDATE_FAILED)
        }
    }


    override fun delete(id:Int){
        // 查不到记录 无法删除, 删除失败
        val categoryOld: Category = categoryMapper.selectById(id) ?: throw MallExceptionT(MallExceptionEnum.DELETE_FAILED)
        val cnt:Int=categoryMapper.deleteById(id)
        if (cnt==0){
            throw MallExceptionT(MallExceptionEnum.DELETE_FAILED)
        }

    }

    override fun listForAdmin(pageNum: Long, pageSize: Long): IPage<Category> {
        val page = Page<Category>(pageNum, pageSize)
        val queryWrapper = QueryWrapper<Category>()
        queryWrapper.orderByAsc("type").orderByAsc("order_num")
        return categoryMapper.selectPage(page, queryWrapper)
    }

    @Cacheable(value = ["listCategoryForCustomer"])
    override fun listCategoryForCustomer():List<CategoryVo>{
        val  categoryVoS=ArrayList<CategoryVo>()
        recursivelyFindCategories(categoryVoS,0)
        return categoryVoS
    }
    fun recursivelyFindCategories(categoryVos: ArrayList<CategoryVo>,parentId:Int){
        //递归获取所有子类项 并组合为一个“目录树”
        val queryWrapper=QueryWrapper<Category>()
        queryWrapper.eq("parent_id",parentId)
        val categories=categoryMapper.selectList(queryWrapper)
        if (!CollectionUtils.isEmpty(categories)){
            for (category in categories){
                val categoryVo=CategoryVo()
                BeanUtils.copyProperties(category,categoryVo)
                categoryVos.add(categoryVo)
                recursivelyFindCategories(categoryVo.childCategory,categoryVo.id!!)
            }
        }
    }


}
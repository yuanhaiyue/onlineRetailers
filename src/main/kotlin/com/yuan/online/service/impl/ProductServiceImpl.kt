package com.yuan.online.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yuan.online.common.Constant
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.dao.ProductMapper
import com.yuan.online.model.from.AddProductReq
import com.yuan.online.model.from.ProductListReq
import com.yuan.online.model.pojo.Product
import com.yuan.online.model.query.ProductListQuery
import com.yuan.online.model.vo.CategoryVo
import com.yuan.online.service.CategoryService
import com.yuan.online.service.ProductService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * 描述   商品服务实现方法
 */
@Service
class ProductServiceImpl:ProductService {

    @Autowired
    lateinit var productMapper:ProductMapper
    @Autowired
    lateinit var categoryService: CategoryService


    override fun add(addProductReq: AddProductReq){
        val product=Product()
        BeanUtils.copyProperties(addProductReq,product)
        val query=QueryWrapper<Product>()
        query.eq("name",addProductReq.name)
        val productOld: Product? = productMapper.selectOne(query)
        if (productOld!=null){
            throw MallExceptionT(MallExceptionEnum.NAME_EXISTED)
        }
        val cnt:Int=productMapper.insert(product)
        if (cnt==0){
            throw MallExceptionT(MallExceptionEnum.CREATE_FAILED)
        }
    }

    override fun update(updateProduct:Product){
        val query=QueryWrapper<Product>()
        query.eq("name",updateProduct.name)
        val productOld:Product?= productMapper.selectOne(query)
        // 同名且不同id 不能继续修改
        if (productOld != null && productOld.id!=updateProduct.id){
            throw MallExceptionT(MallExceptionEnum.NAME_EXISTED)
        }
        val cnt=productMapper.update(updateProduct,null)
        if (cnt==0){
            throw MallExceptionT(MallExceptionEnum.UPDATE_FAILED)
        }

    }

    override fun delete(id:Int){
        val query=QueryWrapper<Product>()
        query.eq("id",id)
        val productOld: Product = productMapper.selectOne(query) ?: throw MallExceptionT(MallExceptionEnum.DELETE_FAILED)
        // 无法查询到记录
        val cnt=productMapper.deleteById(id)
        if (cnt==0){
            throw MallExceptionT(MallExceptionEnum.DELETE_FAILED)
        }

    }

    override fun batchUpdateSellStatus(ids:Array<Int>,sellStatus:Int){
        val update=UpdateWrapper<Product>()
        update.`in`("id",ids)
        val product=Product()
        product.status=sellStatus
        productMapper.update(product,update)
    }

    override fun listForAdmin(pageNum: Long, pageSize: Long): IPage<Product> {
        val query = QueryWrapper<Product>()
        query.orderByDesc("update_time")
        val page = Page<Product>(pageNum, pageSize)
        return productMapper.selectPage(page, query)
    }

    override fun detail(id:Int):Product{
        return productMapper.selectById(id)
    }

    override fun list(productListReq: ProductListReq):IPage<Product>{
        val productListQuery:ProductListQuery
        val queryWrapper=QueryWrapper<Product>()
        //搜索处理
        if (StringUtils.hasLength(productListReq.keyword)){
            queryWrapper.like("name",productListReq.keyword)
        }
        //目录处理
        if (productListReq.categoryId!=null){
            val categoryVoList=categoryService.listCategoryForCustomer(productListReq.categoryId!!)
            val categoryIds=ArrayList<Int>()
            categoryIds.add(productListReq.categoryId!!)
            getCategoryIds(categoryVoList as ArrayList<CategoryVo>,categoryIds)
            queryWrapper.`in`("category_id",categoryIds)
        }
        //排序处理
        if (Constant.PRICE_ASC_DESC.contains(productListReq.orderBy)){
            if (productListReq.orderBy.equals("price desc")){
                queryWrapper.orderByDesc()
            }else{
                queryWrapper.orderByAsc()
            }
        }
        val page=Page<Product>(productListReq.pageNum as Long,productListReq.pageSize as Long)
        return productMapper.selectPage(page,queryWrapper)
    }
    fun getCategoryIds(categoryVoList:ArrayList<CategoryVo>,categoryIds:ArrayList<Int>){

        for (categoryVO in categoryVoList){
            categoryIds.add(categoryVO.id!!)
            if (categoryVO.childCategory.size!=0){
                getCategoryIds(categoryVO.childCategory,categoryIds)
            }
        }
    }

}
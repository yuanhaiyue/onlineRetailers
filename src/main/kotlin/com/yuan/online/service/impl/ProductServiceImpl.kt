package com.yuan.online.service.impl

import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.dao.ProductMapper
import com.yuan.online.model.from.AddProductReq
import com.yuan.online.model.pojo.Product
import com.yuan.online.service.ProductService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 描述   商品服务实现方法
 */
@Service
class ProductServiceImpl:ProductService {

    @Autowired
    lateinit var productMapper:

    override fun add(addProductReq: AddProductReq){
        val product=Product()
        BeanUtils.copyProperties(addProductReq,product)
        val productOld: Product? = productMapper
        if (productOld!=null){
            throw MallExceptionT(MallExceptionEnum.NAME_EXISTED)
        }
        val cnt:Int=productMapper.insert(product)
        if (cnt==0){
            throw MallExceptionT(MallExceptionEnum.CREATE_FAILED)
        }
    }

}
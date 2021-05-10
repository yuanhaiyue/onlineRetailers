package com.yuan.online.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.yuan.online.model.from.AddProductReq
import com.yuan.online.model.from.ProductListReq
import com.yuan.online.model.pojo.Product


/**
 * 描述  商品Service
 */

interface ProductService {
    fun add(addProductReq: AddProductReq)
    fun update(updateProduct: Product)
    fun delete(id: Int)
    fun batchUpdateSellStatus(ids: Array<Int>, sellStatus: Int)

    fun listForAdmin(pageNum:Long,pageSize:Long): IPage<Product>
    fun detail(id: Int): Product
    fun list(productListReq: ProductListReq): IPage<Product>
}
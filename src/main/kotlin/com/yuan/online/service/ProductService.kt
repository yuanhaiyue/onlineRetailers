package com.yuan.online.service

import com.yuan.online.model.from.AddProductReq


/**
 * 描述  商品Service
 */

interface ProductService {
    fun add(addProductReq: AddProductReq)
}
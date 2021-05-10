package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.model.from.ProductListReq
import com.yuan.online.service.ProductService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 描述   前台商品Controller
 */
@RestController
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @ApiOperation("商品详情")
    @GetMapping("/product/detail")
    fun detail(@RequestParam id:Int):ApiResponse{
        val product= productService.detail(id)
        return ApiResponse.success(product)

    }

    @ApiOperation("前台商品列表")
    @GetMapping("/product/list")
    fun list(productListReq: ProductListReq):ApiResponse{
        val iPage=productService.list(productListReq)
        return ApiResponse.success(iPage)
    }
}
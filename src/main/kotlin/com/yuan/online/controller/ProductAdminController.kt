package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.model.from.AddCategoryReq
import com.yuan.online.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class ProductAdminController {

    @Autowired
    lateinit var productService: ProductService

    /**
     * @param addProductReq
     * @return
     */

    @PostMapping("/admin/product/add")
    fun addProduct(@RequestBody @Valid addProductReq: AddCategoryReq):ApiResponse{
        productService.add(addProductReq)
        return ApiResponse.success()
    }

}
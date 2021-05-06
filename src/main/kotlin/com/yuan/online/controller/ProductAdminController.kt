package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.common.Constant
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.from.AddProductReq
import com.yuan.online.service.ProductService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
class ProductAdminController {

    @Autowired
    lateinit var productService: ProductService

    /**
     * @param addProductReq
     * @return
     */
    @ApiOperation("后台增加商品")
    @PostMapping("/admin/product/add")
    fun addProduct(@RequestBody @Valid addProductReq: AddProductReq):ApiResponse{
        productService.add(addProductReq)
        return ApiResponse.success()
    }

    @ApiOperation("后台上传图片")
    @PostMapping("/admin/upload/file")
    fun upload(httpServletRequest: HttpServletRequest,@RequestParam("file") file:MultipartFile):ApiResponse{
        val fileName: String? =file.originalFilename
        val suffixName: String =fileName!!.substring(fileName.indexOf("."))
        val uuid=UUID.randomUUID()
        val newFileName:String=uuid.toString()+suffixName
        val fileDirectory=File(Constant.FILE_UPLOAD_DIR)
        val distFile=File(Constant.FILE_UPLOAD_DIR+newFileName)
        if (!fileDirectory.exists()){
            if (!fileDirectory.mkdir()){
                throw MallExceptionT(MallExceptionEnum.MKDIR_FAILED)
            }
        }
        return ApiResponse.success()
    }
}
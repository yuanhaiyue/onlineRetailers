package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.common.Constant
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.from.AddProductReq
import com.yuan.online.model.from.UpdateProductReq
import com.yuan.online.model.pojo.Product
import com.yuan.online.service.ProductService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.net.URI
import java.net.URISyntaxException
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
        val destFile=File(Constant.FILE_UPLOAD_DIR+"\\"+newFileName)
        if (!fileDirectory.exists()){
            if (!fileDirectory.mkdir()){
                throw MallExceptionT(MallExceptionEnum.MKDIR_FAILED)
            }
        }
        file.transferTo(destFile)
        return try {
            ApiResponse.success("/"+getHost(URI(httpServletRequest.requestURL.toString())).toString()+"images/"+newFileName)
        }catch (e:URISyntaxException){
            ApiResponse.error(MallExceptionEnum.UPLOAD_FAILED)
        }

    }

    private fun getHost(uri: URI): URI? {
        return try {
            URI(uri.scheme, uri.userInfo, uri.host, uri.port, null, null, null)
        } catch (e: URISyntaxException) {
            null
        }
    }

    @ApiOperation("后台更新商品")
    @PostMapping("/admin/product/update")
    fun updateProduct(@Valid @RequestBody updateProductReq: UpdateProductReq):ApiResponse{
        val product= Product()
        BeanUtils.copyProperties(updateProductReq,product)
        productService.update(product)
        return ApiResponse.success()
    }

    @ApiOperation("后台删除商品")
    @PostMapping("/admin/product/delete")
    fun deleteProduct(@RequestParam id:Int):ApiResponse{
        productService.delete(id)
        return ApiResponse.success()
    }

    @ApiOperation("后台批量上下架")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    fun batchUpdateSellStatus(@RequestParam ids:Array<Int>,@RequestParam  sellStatus:Int):ApiResponse{
        productService.batchUpdateSellStatus(ids,sellStatus)
        return ApiResponse.success()
    }

    @ApiOperation("后台商品列表接口")
    @PostMapping("/admin/product/list")
    fun list(@RequestParam pageNum:Long,@RequestParam pageSize:Long):ApiResponse{
        val iPage=productService.listForAdmin(pageNum,pageSize)
        return ApiResponse.success(iPage)
    }


}
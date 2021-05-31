package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.service.OrderService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 描述      订单后台管理Controller
 */

@RestController
@RequestMapping("/admin/order")
class OrderAdminController {

    @Autowired
    lateinit var orderService: OrderService


    @ApiOperation("管理员订单列表")
    @GetMapping("/list")
    fun listForAdmin(@RequestParam pageNum:Long,@RequestParam pageSize:Long):ApiResponse{
        val orderVoList=orderService.listForAdmin(pageNum,pageSize)
        return ApiResponse.success(orderVoList)
    }

    @ApiOperation("管理员发货")
    @PostMapping("/delivered")
    fun delivered(@RequestParam orderNo:String):ApiResponse{
        orderService.deliver(orderNo)
        return ApiResponse.success()
    }

    @ApiOperation("完结订单")
    @PutMapping("/finish")
    fun finish(@RequestParam orderNo: String):ApiResponse{
        orderService.finish(orderNo)
        return ApiResponse.success()
    }
}
package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.model.from.CreateOrderReq
import com.yuan.online.service.OrderService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotNull

/**
 * 描述    订单Controller
 */
@RequestMapping("/order")
@RestController
class OrderController {

    @Autowired
    lateinit var orderService: OrderService

    @ApiOperation("创建订单")
    @PostMapping("/create")
    fun create(@RequestBody createOrderReq: CreateOrderReq):ApiResponse{
        val orderNo:String=orderService.create(createOrderReq)
        return ApiResponse.success(orderNo)
    }

    @ApiOperation("订单详情")
    @GetMapping("/detail")
    fun detail(@RequestParam orderNo:String):ApiResponse{
        val orderVo=orderService.detail(orderNo)
        return ApiResponse.success(orderVo)
    }

    @ApiOperation("前台订单列表")
    @GetMapping("/list")
    fun list(@RequestParam @NotNull pageNum:Long, @RequestParam @NotNull pageSize:Long):ApiResponse{
        val orderVoList=orderService.listForCustomer(pageNum,pageSize)
        return ApiResponse.success(orderVoList)
    }

    /**
     * 订单取消
     */

    @ApiOperation("前台取消订单")
    @PutMapping("/cancel")
    fun cancel(@RequestParam @NotNull orderNo:String):ApiResponse{
        orderService.cancel(orderNo)
        return ApiResponse.success()
    }


    /**
     * 生成支付二维码
     */
    @ApiOperation("生成支付二维码")
    @GetMapping("qrcode")
    fun qrcode(@RequestParam orderNo:String):ApiResponse{
        val pngAddress=orderService.grCode(orderNo)
        return ApiResponse.success(pngAddress)
    }

    /**
     * 支付接口
     */

    @ApiOperation("支付接口")
    @GetMapping("/pay")
    fun pay(@RequestParam orderNo: String):ApiResponse{
        orderService.pay(orderNo)
        return ApiResponse.success()
    }

}
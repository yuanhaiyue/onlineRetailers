package com.yuan.online.service

import com.yuan.online.model.from.CreateOrderReq
import com.yuan.online.model.vo.OrderVo

interface OrderService {
    fun create(createOrderReq: CreateOrderReq): String
    fun detail(orderNo: String): OrderVo?
    fun listForCustomer(pageNum: Long, pageSize: Long): List<OrderVo>
    fun cancel(orderNo: String)
    fun grCode(orderNo: String): String
    fun listForAdmin(pageNum: Long, pageSize: Long): List<OrderVo>
    fun pay(orderNo: String)
    fun deliver(orderNo: String)
    fun finish(orderNo: String)
}
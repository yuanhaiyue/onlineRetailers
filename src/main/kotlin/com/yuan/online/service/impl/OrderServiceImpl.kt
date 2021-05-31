package com.yuan.online.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yuan.online.common.CartStatus
import com.yuan.online.common.Constant
import com.yuan.online.common.OrderStatusEnum
import com.yuan.online.common.SaleStatus
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.filter.UserFilter
import com.yuan.online.model.dao.CartMapper
import com.yuan.online.model.dao.OrderItemMapper
import com.yuan.online.model.dao.OrderMapper
import com.yuan.online.model.dao.ProductMapper
import com.yuan.online.model.from.CreateOrderReq
import com.yuan.online.model.pojo.Order
import com.yuan.online.model.pojo.OrderItem
import com.yuan.online.model.pojo.Product
import com.yuan.online.model.vo.CartVo
import com.yuan.online.model.vo.OrderItemVo
import com.yuan.online.model.vo.OrderVo
import com.yuan.online.service.CartService
import com.yuan.online.service.OrderService
import com.yuan.online.service.UserService
import com.yuan.online.util.OrderCodeFactory
import com.yuan.online.util.QRCodeGenerator
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.collections.ArrayList

@Service
class OrderServiceImpl:OrderService {

    @Autowired
    lateinit var orderMapper: OrderMapper
    @Autowired
    lateinit var cartService: CartService
    @Autowired
    lateinit var productMapper:ProductMapper
    @Autowired
    lateinit var cartMapper: CartMapper

    @Autowired
    lateinit var orderItemMapper: OrderItemMapper
    @Autowired
    lateinit var userService: UserService
    /**
     * 数据库事务
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun create(createOrderReq: CreateOrderReq):String{
        //拿到用户id
        val userId:Int=UserFilter.currentUser.id!!

        //从购物车查找已勾选的商品
        val cartVoList:List<CartVo> =cartService.list(userId)
        val cartVoSList: List<CartVo> =isSelected(cartVoList)

        //如果购物车已勾选为空 则报错
        if (cartVoSList.isEmpty()){
            throw MallExceptionT(MallExceptionEnum.CART_EMPTY)
        }

        //判断商品是否存在、上下架状态、库存
        validSaleStatusAndStock(cartVoSList)

        //把购物车对象转换为订单item对象
        val orderItemList:List<OrderItem> =cartVoListToOrderItemList(cartVoSList)

        //扣库存
        deleteStock(orderItemList)

        //把购物车中的商品 已勾选的删除
        cleanCart(cartVoSList)

        //生成订单
        val order=Order()

        //生成订单号，有独立的规则
        val orderNo:String= OrderCodeFactory.getOrderCode(userId.toLong())
        order.orderNo=orderNo
        order.userId=userId
        order.totalPrice=totalPrice(orderItemList)
        order.receiverName=createOrderReq.receiverName
        order.receiverMobile=createOrderReq.receiverMobile
        order.receiverAddress=createOrderReq.receiverAddress
        order.orderStatus=OrderStatusEnum.NOT_PAID.code
        order.postage=0
        order.paymentType=1

        //插入order表
        orderMapper.insert(order)

        //循环保存每个商品到order_item 表
        for (orderItem in orderItemList){
            orderItem.orderNo=order.orderNo
            orderItemMapper.insert(orderItem)

        }
        //把结果返回
        return orderNo

    }

    /**
     * 计算总价
     */
    private fun totalPrice(orderItemList: List<OrderItem>): Int? {
        var totalPrice:Int=0
        for (orderItem in orderItemList){
            totalPrice+= orderItem.totalPrice!!
        }
        return totalPrice
    }

    /**
     * 从购物车中删除 已勾选 下订单的商品
     */

    private fun cleanCart(cartVoSList: List<CartVo>) {
        for (carVo in cartVoSList){
            cartMapper.deleteById(carVo.id)
        }
    }

    /**
     * 对库存的数值进行扣除 购买的数值
     */

    private fun deleteStock(orderItemList: List<OrderItem>) {
        for (orderItem in orderItemList){
            val product:Product=productMapper.selectById(orderItem.id)
            val stock:Int=product.stock!!- orderItem.quantity!!
            if (stock<0){
                throw MallExceptionT(MallExceptionEnum.NOT_ENOUGH)
            }
            product.stock=stock
            productMapper.updateById(product)
        }
    }

    /**
     * 把购物车对象转换为订单item对象
     */

    private fun cartVoListToOrderItemList(cartVoSList: List<CartVo>):List<OrderItem> {
        val orderItemList=ArrayList<OrderItem>()
        for (cartVo in cartVoSList){
            val orderItem=OrderItem()
            orderItem.productId=cartVo.productId
            //记录商品快照信息
            orderItem.productName=cartVo.productName
            orderItem.productImg=cartVo.productImage
            orderItem.unitPrice=cartVo.price
            orderItem.quantity=cartVo.quantity
            orderItem.totalPrice=cartVo.totalPrice
            orderItemList.add(orderItem)
        }
        return orderItemList
    }

    /**
     * 判断商品的状态是否上架  查看库存
     */

    private fun validSaleStatusAndStock(cartVoSList: List<CartVo>) {

        for (cart in cartVoSList){
            val product: Product? = productMapper.selectById(cart.productId)

            //判断商品是否存在 是否上架
            if (product==null|| product.status == SaleStatus.NOT_SALE){
                throw MallExceptionT(MallExceptionEnum.NOT_SALE)
            }

            //判断商品库存
            if (cart.quantity > product.stock!!){
                throw MallExceptionT(MallExceptionEnum.NOT_ENOUGH)
            }
        }


    }

    /**
     *  剔除购物车中未被勾选的记录
     */

    fun isSelected(cartVoS:List<CartVo>):List<CartVo>{
        val cartVoList= ArrayList<CartVo>()
        for (cart in cartVoS){
            if (cart.selected==CartStatus.CHECKED){
                cartVoList.add(cart)
            }
        }
        return cartVoList
    }


    override fun detail(orderNo:String): OrderVo? {
        //根据 orderNo 获取订单 如果订单不存在则爆出异常
        val order: Order = selectByPrimaryKey(orderNo) ?: throw MallExceptionT(MallExceptionEnum.NO_ORDER)
        //如果订单存在，则需要判定所属
        val userId:Int=UserFilter.currentUser.id!!
        if (order.userId!=userId){
            throw MallExceptionT(MallExceptionEnum.NOT_YOUR_ORDER)
        }
        val orderVo:OrderVo?=getOrderVo(order)
        orderVo?.orderStatusName=OrderStatusEnum.codeOf(orderVo?.orderStatus).value
        return orderVo

    }

    private fun getOrderVo(order: Order): OrderVo? {
        val orderVo=OrderVo()
        BeanUtils.copyProperties(order,orderVo)
        //获取订单对应的orderItemVoList
        val orderItemList:List<OrderItem> =selectByOrderNo(order.orderNo)
        val orderItemVoList:List<OrderItemVo> =orderItemChange(orderItemList)
        orderVo.orderItemList=orderItemVoList
        return orderVo
    }

    private fun orderItemChange(orderItemList: List<OrderItem>): List<OrderItemVo> {
        val orderItemVoList=ArrayList<OrderItemVo>()
        for (orderItem in orderItemList){
            val orderItemVo=OrderItemVo()
            BeanUtils.copyProperties(orderItem,orderItemVo)
            orderItemVoList.add(orderItemVo)
        }
        return orderItemVoList
    }

    private fun selectByOrderNo(orderNo: String?): List<OrderItem> {
        val query=QueryWrapper<OrderItem>()
        query.eq("order_no",orderNo)
        return orderItemMapper.selectList(query)
    }


    private fun selectByPrimaryKey(orderNo: String):Order?{
        val query=QueryWrapper<Order>()
        query.eq("order_no",orderNo)
        return orderMapper.selectOne(query)
    }


    override fun listForCustomer(pageNum: Long, pageSize: Long): List<OrderVo> {
        //获取用户id
        val userId = UserFilter.currentUser.id
        //配置分页参数
        val page = Page<Order>(pageNum, pageSize)
        //通过id 查询订单列表
        val iPage = selectForCustomer(userId!!, page)
        val orderList: List<Order> = iPage!!.records
        //转换为OrderVo 并返回
        return orderListToOrderVoList(orderList)
    }

    /**
     * Order列表 转换为 OrderVo
     */

    private fun orderListToOrderVoList(orderList: List<Order>): List<OrderVo> {
        val orderVoList=ArrayList<OrderVo>()
        for (order in orderList){
            val orderVo:OrderVo?=getOrderVo(order)
            if (orderVo != null) {
                orderVoList.add(orderVo)
            }
        }
        return orderVoList
    }

    /**
     * 用户查询订单列表
     */
    private fun selectForCustomer(userId:Int,page:Page<Order>): Page<Order>? {
        val query=QueryWrapper<Order>()
        query.eq("user_id",userId).orderByDesc("create_time")
        return orderMapper.selectPage(page,query)
    }

    /**
     * 用户取消订单
     */

    override fun cancel(orderNo:String){
        //根据 orderNo 查询订单信息
        // 查询不到订单 ,报错
        val order= selectByPrimaryKey(orderNo) ?: throw MallExceptionT(MallExceptionEnum.NO_ORDER)
        //验证用户身份
        val userId:Int=UserFilter.currentUser.id!!
        if (order.userId!=userId){
            throw MallExceptionT(MallExceptionEnum.NOT_YOUR_ORDER)
        }
        // 查看订单状态是否符合
        if (order.orderStatus == OrderStatusEnum.NOT_PAID.code){
            order.orderStatus=OrderStatusEnum.CANCELED.code
            order.endTime= Timestamp(System.currentTimeMillis())
            orderMapper.updateById(order)
        }else{
            throw MallExceptionT(MallExceptionEnum.WRONG_ORDER_STATUS)
        }

    }

    override fun grCode(orderNo: String):String{
        val attributes:ServletRequestAttributes=RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request:HttpServletRequest=attributes.request
        val address:String=Constant.FILE_UPLOAD_IP+request.localPort
        val payUrl:String="http://"+address+"/pay?orderNo="+orderNo
        QRCodeGenerator.generateQRCodeImage(payUrl,350,350,Constant.FILE_UPLOAD_DIR+"\\"+orderNo+".png")
        val pngAddress:String="http://"+address+"/images/"+orderNo+".png"

        return pngAddress
    }

    override fun listForAdmin(pageNum: Long, pageSize: Long): List<OrderVo> {

        //配置分页参数
        val page = Page<Order>(pageNum, pageSize)
        //分页查询数据
        val iPage = selectAllForAdmin(page)
        val orderList: List<Order> = iPage.records
        //转换为OrderVo 并返回
        return orderListToOrderVoList(orderList)
    }

    fun selectAllForAdmin(page:Page<Order>):IPage<Order>{
       return orderMapper.selectPage(page,null)
    }


    override fun pay(orderNo: String){
        //根据 orderNo 查询订单信息
        // 查询不到订单 ,报错
        val order= selectByPrimaryKey(orderNo) ?: throw MallExceptionT(MallExceptionEnum.NO_ORDER)

        if (order.orderStatus==OrderStatusEnum.NOT_PAID.code){
            order.orderStatus=OrderStatusEnum.PAID.code
            order.payTime=Timestamp(System.currentTimeMillis())
            orderMapper.updateById(order)
        }else{
            throw MallExceptionT(MallExceptionEnum.WRONG_ORDER_STATUS)
        }


    }

    override fun deliver(orderNo: String) {
        //根据 orderNo 查询订单信息
        // 查询不到订单 ,报错
        val order= selectByPrimaryKey(orderNo) ?: throw MallExceptionT(MallExceptionEnum.NO_ORDER)

        if (order.orderStatus==OrderStatusEnum.PAID.code){
            order.orderStatus=OrderStatusEnum.DELIVERED.code
            order.deliveryTime=Timestamp(System.currentTimeMillis())
            orderMapper.updateById(order)
        }else{
            throw MallExceptionT(MallExceptionEnum.WRONG_ORDER_STATUS)
        }
    }


    override fun finish(orderNo: String){
        //根据 orderNo 查询订单信息
        // 查询不到订单 ,报错
        val order= selectByPrimaryKey(orderNo) ?: throw MallExceptionT(MallExceptionEnum.NO_ORDER)
        //如果是普通用户 就要校验订单的所属
        if (!userService.checkAdminRole(UserFilter.currentUser) && order.userId!=UserFilter.currentUser.id){
            throw MallExceptionT(MallExceptionEnum.NOT_YOUR_ORDER)
        }

        //发货后可以完结订单

        if (order.orderStatus==OrderStatusEnum.DELIVERED.code){
            order.orderStatus=OrderStatusEnum.FINISHED.code
            order.endTime=Timestamp(System.currentTimeMillis())
            orderMapper.updateById(order)
        }else{
            throw MallExceptionT(MallExceptionEnum.WRONG_ORDER_STATUS)
        }


    }








}
package com.yuan.online.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.yuan.online.common.CartStatus
import com.yuan.online.common.Constant
import com.yuan.online.common.SaleStatus
import com.yuan.online.exception.MallExceptionEnum

import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.dao.CartMapper
import com.yuan.online.model.dao.ProductMapper
import com.yuan.online.model.pojo.Cart
import com.yuan.online.model.pojo.Product
import com.yuan.online.model.vo.CartVo
import com.yuan.online.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 描述    购物车Service
 */

@Service
class CartServiceImpl :CartService {

    @Autowired
    lateinit var productMapper: ProductMapper
    @Autowired
    lateinit var cartMapper: CartMapper

    /**
     * 新增购物车
     */
    override fun add(userId:Int,productId:Int,count:Int):List<CartVo>? {
        validProduct(productId,count)

        val wrapper=QueryWrapper<Cart>()
        wrapper.eq("user_id",userId).eq("product_id",productId)
        val cart:Cart?=cartMapper.selectOne(wrapper)
        if (cart==null){
            //此商品不再目录中 需要新增一个记录
            val cartNews=Cart()
            cartNews.userId=userId
            cartNews.productId=productId
            cartNews.quantity=count
            cartNews.selected=CartStatus.CHECKED
            cartMapper.insert(cartNews)
        }else{
            //此商品已经在购物车中 则数量相加
            val countNew=count+ cart.quantity!!
            val cartNew=Cart()
            cartNew.quantity=countNew
            cartNew.productId=cart.productId
            cartNew.userId=cart.userId
            cartNew.id=cart.id
            cartNew.selected=CartStatus.CHECKED
            cartMapper.updateById(cartNew)
        }
        return list(userId)
    }

    /**
     * 判断商品 状态是否正常
     */
    private fun validProduct(productId: Int, count: Int) {
        val product: Product? = productMapper.selectById(productId)

        //判断商品是否存在 是否上架
        if (product==null|| product.status == SaleStatus.NOT_SALE){
            throw MallExceptionT(MallExceptionEnum.NOT_SALE)
        }

        //判断商品库存
        if (count> product.stock!!){
            throw MallExceptionT(MallExceptionEnum.NOT_ENOUGH)
        }

    }

    /**
     * 获取购物车列表
     */
    override fun list(userId:Int):List<CartVo>{
        val cartVOS:List<CartVo> =cartMapper.selectList(userId)
        for (cartVo in cartVOS){
            cartVo.totalPrice=cartVo.price*cartVo.quantity
        }
        return cartVOS
    }


    override fun update(userId:Int,productId:Int,count:Int):List<CartVo>? {
        validProduct(productId,count)

        val wrapper=QueryWrapper<Cart>()
        wrapper.eq("user_id",userId).eq("product_id",productId)
        val cart:Cart?=cartMapper.selectOne(wrapper)
        if (cart==null){
            //更新操作是 在购物车中未找到记录
            throw MallExceptionT(MallExceptionEnum.UPDATE_FAILED)
        }else{
            //此商品已经在购物车中 则更新数量
            val cartNew=Cart()
            cartNew.quantity=count
            cartNew.productId=cart.productId
            cartNew.userId=cart.userId
            cartNew.id=cart.id
            cartNew.selected=CartStatus.CHECKED
            cartMapper.updateById(cartNew)
        }
        return list(userId)
    }


    override fun delete(userId:Int,productId:Int):List<CartVo>? {
        val wrapper=QueryWrapper<Cart>()
        wrapper.eq("user_id",userId).eq("product_id",productId)
        val cart:Cart?=cartMapper.selectOne(wrapper)
        if (cart==null){
            //购物车中没有此商品记录，无法删除
            throw MallExceptionT(MallExceptionEnum.DELETE_FAILED)
        }else{
            //此商品在记录在购物车中，则删除记录
            cartMapper.deleteById(cart.id)
        }
        return this.list(userId)
    }
}
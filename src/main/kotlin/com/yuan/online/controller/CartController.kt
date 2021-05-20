package com.yuan.online.controller

import com.yuan.online.common.ApiResponse
import com.yuan.online.filter.UserFilter
import com.yuan.online.model.vo.CartVo
import com.yuan.online.service.CartService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

/**
 * 描述     购物车Controller
 */
@RequestMapping("/cart")
@RestController
class CartController {

    @Autowired
    lateinit var cartService: CartService

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    fun add(@RequestParam productId:Int,@RequestParam count:Int):ApiResponse{
        val cartS:List<CartVo>? =cartService.add(UserFilter.currentUser.id!!,productId,count)
        return ApiResponse.success(cartS)
    }

    @ApiOperation("购物车列表")
    @GetMapping("/list")
    fun list():ApiResponse{
        //内部获取用户id 防止横向越权
        val id:Int=UserFilter.currentUser.id!!
        val cartS:List<CartVo> =cartService.list(id)
        return ApiResponse.success(cartS)
    }

    @ApiOperation("更新购物车内容")
    @PutMapping("/update")
    fun update(@RequestParam productId:Int,@RequestParam count:Int):ApiResponse{
        //内部获取用户id 防止横向越权
        val id:Int=UserFilter.currentUser.id!!
        val cartS:List<CartVo>? =cartService.update(id,productId, count)
        return ApiResponse.success(cartS)
    }

    @ApiOperation("删除购物车内容")
    @DeleteMapping("/delete")
    fun delete(@RequestParam productId: Int):ApiResponse{
        //不能传入userId cartId 否则可能会删除别人的购物车
        val id:Int=UserFilter.currentUser.id!!
        val cartS:List<CartVo>? =cartService.delete(id,productId)
        return ApiResponse.success(cartS)
    }

}
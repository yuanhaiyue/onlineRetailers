package com.yuan.online.service

import com.yuan.online.model.vo.CartVo


interface CartService {


    fun add(userId: Int, productId: Int, count: Int): List<CartVo>?
    fun list(userId: Int): List<CartVo>
    fun update(userId: Int, productId: Int, count: Int): List<CartVo>?
    fun delete(userId: Int, productId: Int): List<CartVo>?
    fun selectOrNot(userId: Int, productId: Int?, selected: Int): List<CartVo>
    fun selectAllOrNot(userId: Int, selected: Int): List<CartVo>
}

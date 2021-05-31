package com.yuan.online.filter

import com.yuan.online.common.ApiResponse
import com.yuan.online.common.Constant

import com.yuan.online.model.pojo.User
import com.yuan.online.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class AdminFilter: Filter {

    @Autowired
    lateinit var userService: UserService

    override fun init(filterConfig: FilterConfig?) {
        super.init(filterConfig)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req=request as HttpServletRequest
        val resp=response as HttpServletResponse
        resp.contentType="test/html;charset=utf-8"
        val session:HttpSession=req.session
        val user: User? = session.getAttribute(Constant.MALL_USER) as User?
        if (user==null){
            resp.writer.println("{ \nstatus: 10007,\nmsg: 用户未登录,\n data: null} ")
            return
        }
        val adminRole:Boolean=userService.checkAdminRole(user)
        if (adminRole){
            chain?.doFilter(req,resp)
        }else{
            resp.writer.println("{ \nstatus: 10009,\nmsg: 没有管理员权限,\n data: null} ")
        }
    }
}
package com.yuan.online.filter

import com.yuan.online.common.ApiResponse
import com.yuan.online.common.Constant
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.exception.MallExceptionT
import com.yuan.online.model.pojo.User
import com.yuan.online.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
/**
    描述     用户过滤
 */
class UserFilter: Filter {

    companion object{
        @JvmStatic
        lateinit var currentUser:User
    }

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
       chain!!.doFilter(req,resp)
    }
}
package com.yuan.online.controller

import com.baomidou.mybatisplus.core.metadata.IPage
import com.yuan.online.common.ApiResponse
import com.yuan.online.common.Constant
import com.yuan.online.exception.MallExceptionEnum
import com.yuan.online.model.from.AddCategoryReq
import com.yuan.online.model.from.UpdateCategoryReq
import com.yuan.online.model.pojo.Category
import com.yuan.online.model.pojo.User
import com.yuan.online.model.vo.CategoryVo
import com.yuan.online.service.CategoryService
import com.yuan.online.service.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
class CategoryController {

    @Autowired
    lateinit var userService:UserService
    @Autowired
    lateinit var categoryService: CategoryService

    /**
     * 后台添加目录操作
     * @param addCategoryReq
     * @return
     */
    @ApiOperation("后台添加目录")
    @PostMapping("/admin/category/add")
    fun addCategory(session:HttpSession, @RequestBody @Valid addCategoryReq: AddCategoryReq):ApiResponse{

        val currentUser: User = session.getAttribute(Constant.MALL_USER) as User?
            ?: return ApiResponse.error(MallExceptionEnum.NEED_LOGIN)
        //校验是否是管理员
        val adminRole:Boolean=userService.checkAdminRole(currentUser)
        return if (adminRole){
            categoryService.add(addCategoryReq)
            ApiResponse.success()
        }else{
            ApiResponse.error(MallExceptionEnum.NEED_ADMIN)
        }
    }

    /**
     * 更新信息
     * @param session
     * @param updateCategoryReq
     * @return
     */
    @ApiOperation("后台更新目录")
    @PostMapping("/admin/category/update")
    fun updateCategory(@RequestBody @Valid updateCategoryReq: UpdateCategoryReq,session: HttpSession):ApiResponse{

        val currentUser: User = session.getAttribute(Constant.MALL_USER) as User?
            ?: return ApiResponse.error(MallExceptionEnum.NEED_LOGIN)
        //校验是否是管理员
        val adminRole:Boolean=userService.checkAdminRole(currentUser)
        return if (adminRole){
            //是管理员 进行操作
            val category=Category()
            BeanUtils.copyProperties(updateCategoryReq,category)
            categoryService.update(category)
            ApiResponse.success()
        }else{
            ApiResponse.error(MallExceptionEnum.NEED_ADMIN)
        }
    }

    /**
     * 根据目录id 删除
     * @param id
     * @return ApiResponse
     */

    @ApiOperation("后台删除目录")
    @PostMapping("/admin/category/delete")
    fun deleteCategory(@RequestParam id:Int):ApiResponse{
        categoryService.delete(id)
        return ApiResponse.success()
    }

    /**
     * 后台分页查看目录页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("后台目录分页列表")
    @PostMapping("/admin/category/list")
    fun listCategoryAdmin(@RequestParam pageNum:Long,@RequestParam pageSize:Long):ApiResponse{
        val iPage:IPage<Category> = categoryService.listForAdmin(pageNum,pageSize)
        return ApiResponse.success(iPage)
    }

    /**
     * 前台查看目录列表
     * @return
     */
    @ApiOperation("前台目录列表")
    @PostMapping("/category/list")
    fun listCategoryCustomer():ApiResponse{
        val categoryVos:List<CategoryVo> = categoryService.listCategoryForCustomer()
        return ApiResponse.success(categoryVos)
    }
}
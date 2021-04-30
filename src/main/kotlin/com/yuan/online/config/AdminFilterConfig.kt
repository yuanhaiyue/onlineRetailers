package com.yuan.online.config

import com.yuan.online.filter.AdminFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *  admin 过滤器配置
 */
@Configuration
class AdminFilterConfig {

    @Bean
    fun adminFilter():AdminFilter{
        return AdminFilter()
    }
    @Bean(name = ["adminFilterConf"])
    fun adminFilterConfig():FilterRegistrationBean<AdminFilter>{
        val filterRegistrationBean=FilterRegistrationBean<AdminFilter>()
        filterRegistrationBean.filter=adminFilter()
        filterRegistrationBean.addUrlPatterns("/admin/category/*")
        filterRegistrationBean.addUrlPatterns("/admin/product/*")
        filterRegistrationBean.addUrlPatterns("/admin/order/*")
        filterRegistrationBean.setName("adminFilterConfig")
        return filterRegistrationBean
    }



}
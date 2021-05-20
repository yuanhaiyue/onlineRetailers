package com.yuan.online.config

import com.yuan.online.filter.AdminFilter
import com.yuan.online.filter.UserFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *  User 过滤器配置
 */
@Configuration
class UserFilterConfig {

    @Bean
    fun userFilter(): UserFilter {
        return UserFilter()
    }
    @Bean(name = ["adminFilterConf"])
    fun adminFilterConfig():FilterRegistrationBean<UserFilter>{
        val filterRegistrationBean=FilterRegistrationBean<UserFilter>()
        filterRegistrationBean.filter=userFilter()
        filterRegistrationBean.addUrlPatterns("/order/*")
        filterRegistrationBean.addUrlPatterns("/cart/*")
        filterRegistrationBean.setName("userFilterConfig")
        return filterRegistrationBean
    }



}
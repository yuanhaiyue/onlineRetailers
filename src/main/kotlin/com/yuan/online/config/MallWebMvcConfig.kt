package com.yuan.online.config

import com.yuan.online.common.Constant
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MallWebMvcConfig:WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        // 解决静态资源无法访问
        registry.addResourceHandler("/images/**")
            .addResourceLocations("file:/C:/Users/14760/Desktop/images/");
//        // 解决swagger无法访问
//        registry.addResourceHandler("/swagger-ui.html")
//            .addResourceLocations("classpath:/META-INF/resources/");
//        // 解决swagger的js文件无法访问
//        registry.addResourceHandler("/webjars/**")
//            .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry)
    }
}
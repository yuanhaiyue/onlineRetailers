package com.yuan.online

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import springfox.documentation.swagger2.annotations.EnableSwagger2

@MapperScan("com.yuan.online.model.dao")
@SpringBootApplication
@EnableSwagger2
@EnableCaching
class OnlineRetailersApplication

fun main(args: Array<String>) {
    runApplication<OnlineRetailersApplication>(*args)
}

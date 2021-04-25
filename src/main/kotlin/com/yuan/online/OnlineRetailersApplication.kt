package com.yuan.online

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
@MapperScan("com.yuan.online.model.dao")
@SpringBootApplication
class OnlineRetailersApplication

fun main(args: Array<String>) {
    runApplication<OnlineRetailersApplication>(*args)
}

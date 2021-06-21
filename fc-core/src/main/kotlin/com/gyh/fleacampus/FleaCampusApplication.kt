package com.gyh.fleacampus

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@MapperScan("com.gyh.fleacampus.mapper")
@EnableTransactionManagement
class FleaCampusApplication

fun main(args: Array<String>) {
    runApplication<FleaCampusApplication>(*args)
}

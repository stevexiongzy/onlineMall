package com.xzy.core.common;

import org.aspectj.weaver.ast.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(value = "com.xzy.core.common.demo")
@EnableAsync
@EnableTransactionManagement
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class,args);
    }
}

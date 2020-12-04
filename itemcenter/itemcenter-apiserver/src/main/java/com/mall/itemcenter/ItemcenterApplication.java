package com.mall.itemcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiongziyuan
 */
@SpringBootApplication
//@EnableApolloConfig
//@EnableDiscoveryClient
@MapperScan("com.mall.itemcenter.dao")
public class ItemcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemcenterApplication.class, args);
    }

}

package com.xzy.core.common.demo;

import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/getTest")
    public String getTest(@RequestParam("name")String name,@RequestParam("id") String id){
        if(Objects.equals(name,"xzy")) {
            int a = 1 / 0;
        }
        return name;
    }

    @GetMapping("/redisson")
    public String testRedisson() throws IOException {
        return redissonClient.getConfig().toJSON().toString();
    }
}

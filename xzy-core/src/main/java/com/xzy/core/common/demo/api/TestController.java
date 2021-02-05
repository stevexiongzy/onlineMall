package com.xzy.core.common.demo.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzy.core.common.annotation.RedisLockAnnotation;
import com.xzy.core.common.constant.RedisLockTypeEnum;
import com.xzy.core.common.demo.Good;
import com.xzy.core.common.demo.GoodMapper;
import com.xzy.core.common.persistence.IBaseService;
import com.xzy.core.common.persistence.IBaseServiceImpl;
import com.xzy.core.common.persistence.QueryConditionEnum;
import com.xzy.core.common.web.ResultDTO;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RedissonClient redissonClient;

    private GoodMapper goodMapper;

    IBaseService<Good> iBaseService;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public TestController(GoodMapper goodMapper){
        this.goodMapper = goodMapper;
        this.iBaseService = new IBaseServiceImpl<>(goodMapper);

    }

    @GetMapping("/getTest")
    public String getTest(@RequestParam("name")String name,@RequestParam("id") String id){
        if(Objects.equals(name,"xzy")) {
            int a = 1 / 0;
        }
        return name;
    }

    @GetMapping("/redisson")
    @RedisLockAnnotation(lockFiled = 0,tryTime = 0,typeEnum = RedisLockTypeEnum.ONE,lockTime = 60)
    public ResultDTO<String> testRedisson(@RequestParam("name") String name) throws IOException, InterruptedException {
        return ResultDTO.ok(redissonClient.getConfig().toJSON().toString());
    }

    @GetMapping("/mp")
    public ResultDTO<Good> testMP() throws IOException {
        QueryWrapper<Good> eq = new QueryWrapper<>();
//        eq.eq("name_ext","111");
        QueryConditionEnum conditionEnum = QueryConditionEnum.valueOf("like");
        conditionEnum.getQuery(eq, "name_ext", "1");
        List<Good> one = iBaseService.list(eq);
        String toString = redissonClient.getConfig().toJSON().toString();
        return ResultDTO.ok(one);
    }

}

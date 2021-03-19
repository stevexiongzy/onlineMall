package com.xzy.core.common.persistence.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.xzy.core.common.constant.CommonConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *  mp 分页插件
 * @author xzy
 */
@Slf4j
@Component
@EnableConfigurationProperties(CommonConfigProperties.class)
public class PageInterceptor extends PaginationInnerInterceptor {

    @Autowired
    private CommonConfigProperties commonConfigProperties;

    public PageInterceptor(){}


    public PageInterceptor(String dbType){
        super();
        //设置数据库类型 默认mysql
        super.setDbType(DbType.valueOf(dbType));
        //设置默认不限制条数
        super.setMaxLimit(-1L);
    }
}

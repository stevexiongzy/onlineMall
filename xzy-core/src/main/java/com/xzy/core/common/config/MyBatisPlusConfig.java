package com.xzy.core.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.xzy.core.common.persistence.interceptor.PageInterceptor;
import com.xzy.core.common.persistence.interceptor.TenantInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author xzy
 * @Description: mp 配置类，主要为sql注入器
 */
@Configuration
@EnableConfigurationProperties
public class MyBatisPlusConfig  {

    @Value("${xzy.core.dbType:MYSQL}")
    private String dbType;

    /**
     * 需要过滤的表，使用逗号分隔
     */
    @Value("xzy.core.ignoreTables")
    private String ignoreTables;
    /**
     * 数据库租户列 列名 默认tenant_id
     */
    @Value("${xzy.core.tenantColumnName:tenant_id}")
    private String tenantColumnName;

    /**
     * mp插件集合
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //多租户
        mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantInterceptor(ignoreTables,tenantColumnName)));
        //分页
        mybatisPlusInterceptor.addInnerInterceptor(new PageInterceptor(dbType));
        return mybatisPlusInterceptor;
    }

}

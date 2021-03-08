package com.xzy.core.common.config;

import com.xzy.core.common.web.ExtraParamInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 拦截器配置类
 * @author xzy
 */
@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(extraParamInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public ExtraParamInterceptor extraParamInterceptor(){
        return new ExtraParamInterceptor();
    }
}

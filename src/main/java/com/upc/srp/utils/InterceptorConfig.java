package com.upc.srp.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author by zhoutao
 * @description 拦截器，用于拦截未授权的操作
 * @date 2019/12/27 10:59
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/key", "/auth/login", "/auth/register", "/error","/pre/**");
    }

    @Bean
    public AuthInterceptor securityInterceptor(){
        return new AuthInterceptor();
    }
}

package com.celest.matchingsystem.config;

import com.celest.matchingsystem.interceptors.IpAddressInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IpAddressInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error/**");
    }
}

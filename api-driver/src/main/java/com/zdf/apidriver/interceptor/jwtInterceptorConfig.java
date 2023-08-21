package com.zdf.apidriver.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class jwtInterceptorConfig implements WebMvcConfigurer
{
    @Bean
    public jwtInterceptor jwtInterceptor()
    {
        return new jwtInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/noauthtest")
                .excludePathPatterns("/verification-code")
                .excludePathPatterns("/verification-code-check")
                .excludePathPatterns("/token-refresh")
                .excludePathPatterns("/driver-user-work-status")
                .excludePathPatterns("/driver-car-binding-relationship/get")
        ;
    }
}

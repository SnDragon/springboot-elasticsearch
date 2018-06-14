package com.scut.longerwu.graduation.config;

import com.scut.longerwu.graduation.interceptors.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    /**
     * 把拦截器注入为bean
     * @return
     */
//    @Bean
//    public HandlerInterceptor getAuthInterceptor(){
//        return new AuthInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // addPathPatterns 用于添加拦截规则
//        // excludePathPatterns 用于排除拦截
//        registry.addInterceptor(getAuthInterceptor())
//                .addPathPatterns("/api/**")
//                .excludePathPatterns("/api/staffs/*Login*");
//        super.addInterceptors(registry);
//    }
}
package com.asuna.store.config;

import com.asuna.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

/**
 * 处理器拦截的注册
 */
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {


    /** 配置拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 创建自定义的拦截器对象
        HandlerInterceptor handlerInterceptor = new LoginInterceptor();
        // 配置白名单: 存放在一个List集合中
        ArrayList<String> list = new ArrayList<>();
        list.add("/bootstrap3/**");
        list.add("/css/**");
        list.add("/images/**");
        list.add("/js/**");
        list.add("/web/register.html");
        list.add("/web/login.html");
        list.add("/web/index.html");
        list.add("/web/product.html");
        list.add("/users/reg");
        list.add("/users/login");

        registry.addInterceptor(handlerInterceptor)
                .addPathPatterns("/**") // 要拦截的url
                .excludePathPatterns(list); // 要放行的url

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}

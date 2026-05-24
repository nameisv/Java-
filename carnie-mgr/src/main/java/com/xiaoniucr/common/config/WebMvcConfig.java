package com.xiaoniucr.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web控制器
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    /**
     * 资源映射配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置资源映射
         * 意思是：如果访问的资源路径是以“/upload/”开头的，
         * 就给我映射到本机的filePath本地路径
         */
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:"+System.getProperty("user.dir") + "/files/");
    }


    /**
     * 拦截器注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).
                addPathPatterns("/**").
                excludePathPatterns("/login.html","/register.html","/login","/register",
                        "/css/**","/icons/**","/js/**","/media/**","/vendors/**","/fonts/**","/images/**","/lib/**","/ueditor/**",
                        "/webjars/**");
    }



}

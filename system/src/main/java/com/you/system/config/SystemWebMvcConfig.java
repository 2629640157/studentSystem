package com.you.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 游斌
 * @create 2020-12-13  12:20
 */
@Configuration
public class SystemWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/user/do/to/main").setViewName("main");
        registry.addViewController("/user/do/to/login").setViewName("login");
        registry.addViewController("/user/do/to/register").setViewName("reg");
        registry.addViewController("//user/to/approve").setViewName("accttype");


        registry.addViewController("/clbum/to/get/all").setViewName("clbum");
//        registry.addViewController("/exam/get/report/figure").setViewName("report-figure");


    }
}

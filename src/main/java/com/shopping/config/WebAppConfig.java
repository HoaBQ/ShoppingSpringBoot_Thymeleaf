package com.shopping.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebAppConfig {

    @Bean
    public FilterRegistrationBean<AdminAccessFilter> adminAccessFilter() {
        FilterRegistrationBean<AdminAccessFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new AdminAccessFilter());
        filter.addUrlPatterns("/admin", "/admin/*");
        filter.setOrder(1);
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
package com.JavaTech.PointOfSales.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer {




    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/change-password-first-time").setViewName("/users/page-change-pw");
    }
}
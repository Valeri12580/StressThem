package com.stressthem.app.config;

import org.springframework.stereotype.Component;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class MVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/faq").setViewName("faq");
        registry.addViewController("/contact").setViewName("contact-us");

    }
}

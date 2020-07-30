package com.stressthem.app.config;

import com.stressthem.app.web.interceptors.FaviconInterceptor;
import com.stressthem.app.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class MVCConfig implements WebMvcConfigurer {

    private TitleInterceptor titleInterceptor;
    private FaviconInterceptor faviconInterceptor;

    @Autowired
    public MVCConfig(TitleInterceptor titleInterceptor, FaviconInterceptor faviconInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/faq").setViewName("faq");
        registry.addViewController("/contact").setViewName("contact-us");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(titleInterceptor);
        registry.addInterceptor(faviconInterceptor);
    }
}

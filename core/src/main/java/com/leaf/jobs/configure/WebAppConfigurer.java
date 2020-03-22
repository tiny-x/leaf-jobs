package com.leaf.jobs.configure;

import com.leaf.jobs.web.intercept.SessionIntercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc config
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new SessionIntercept());
        registration.addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/login.html",
                        "/error",
                        "/api/**",
                        "/css/**",
                        "/images/**",
                        "/js/**",
                        "/lib/**",
                        "/page/**");
    }

}

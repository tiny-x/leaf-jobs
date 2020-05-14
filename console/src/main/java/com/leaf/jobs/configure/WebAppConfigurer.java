package com.leaf.jobs.configure;

import com.leaf.jobs.web.intercept.SessionIntercept;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * mvc config
 * @author yefei
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

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

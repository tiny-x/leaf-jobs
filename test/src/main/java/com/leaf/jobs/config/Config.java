package com.leaf.jobs.config;

import com.leaf.jobs.spi.spring.support.JobsProperties;
import com.leaf.jobs.spi.spring.support.JobsScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.leaf.jobs.spi")
@JobsScanner(basePackages = "com.leaf.jobs")
public class Config {

    @Bean
    public JobsProperties jobsProperties() {
        JobsProperties jobsProperties = new JobsProperties();
        jobsProperties.setRegisterAddress("zookeeper.dev.xianglin.com:2181");
        jobsProperties.setSystemName("test");
        return jobsProperties;
    }
}
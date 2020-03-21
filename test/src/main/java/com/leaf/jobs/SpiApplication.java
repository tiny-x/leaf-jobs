package com.leaf.jobs;

import com.leaf.jobs.spi.spring.support.JobsScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@JobsScanner(basePackages = "com.leaf.jobs")
public class SpiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpiApplication.class, args);
    }
}

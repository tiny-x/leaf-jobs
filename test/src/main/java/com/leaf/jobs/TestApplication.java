package com.leaf.jobs;

import com.leaf.jobs.support.spring.JobsScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@JobsScanner(basePackages = "com.leaf.jobs")
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}

package com.leaf.jobs;

import com.leaf.jobs.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpiTest {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(Config.class);
        configApplicationContext.start();
        Thread.currentThread().join();
    }
}

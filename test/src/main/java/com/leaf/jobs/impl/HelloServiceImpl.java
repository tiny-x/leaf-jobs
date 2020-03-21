package com.leaf.jobs.impl;

import com.leaf.jobs.HelloService;
import com.leaf.jobs.spi.JobsProvider;
import lombok.extern.slf4j.Slf4j;

@JobsProvider(group = "rjb")
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        log.info("hi service" + name);
        return "sb: " + name;
    }

    @Override
    public String sayHello2(String name, String age) {
        log.info("hi service" + name);
        return "sb: " + name + age;
    }
}

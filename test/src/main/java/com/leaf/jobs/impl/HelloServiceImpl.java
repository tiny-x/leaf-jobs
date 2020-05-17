package com.leaf.jobs.impl;

import com.leaf.jobs.HelloService;
import com.leaf.jobs.model.User;
import com.leaf.jobs.support.JobsProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@JobsProvider(group = "rjb")
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public User sayHello(String name) {
        log.info("hi service" + name);
        User user = new User();
        user.setName(name);
        user.setAge(String.valueOf(new Random().nextInt()));
        user.setProfile("i'm king");
        return user;
    }

    @Override
    public String sayHello2(String name, String age) {
        log.info("hi service" + name);
        return "ss: " + name + age;
    }
}

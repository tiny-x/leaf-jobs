package com.leaf.jobs.impl;

import com.leaf.jobs.HelloService;
import com.leaf.jobs.model.User;
import com.leaf.jobs.support.JobsProvider;
import lombok.extern.slf4j.Slf4j;

@JobsProvider(group = "rjb")
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public User sayHello(User user1, User user2) {
        log.info("hi user1, " + user1.getName());
        log.info("hi user2, " + user2.getName());
        user1.setProfile("i'm king");
        return user1;
    }

    @Override
    public String sayHello2(String name, String age) {
        log.info("hi service" + name);
        return "ss: " + name + age;
    }
}

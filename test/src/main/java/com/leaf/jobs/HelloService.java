package com.leaf.jobs;

import com.leaf.jobs.model.User;

public interface HelloService {

    User sayHello(User user1, User user2);

    String sayHello2(String name, String age);
}

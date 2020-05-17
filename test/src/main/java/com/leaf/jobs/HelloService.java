package com.leaf.jobs;

import com.leaf.jobs.model.User;

public interface HelloService {

    User sayHello(String name);

    String sayHello2(String name, String age);
}

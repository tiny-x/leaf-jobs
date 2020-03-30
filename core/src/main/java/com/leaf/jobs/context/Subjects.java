package com.leaf.jobs.context;

import com.leaf.jobs.dao.model.User;

public class Subjects {

    private final static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static User getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void setUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

}

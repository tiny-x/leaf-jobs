package com.leaf.jobs.service;

import com.leaf.jobs.dao.model.User;
import com.leaf.jobs.model.Response;

public interface UserService {

    Response login(User user);

    Response<User> selectUser(User user) throws Exception;

    Response addUser(User user);
}

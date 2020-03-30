package com.leaf.jobs.service;

import com.leaf.jobs.dao.model.User;
import com.leaf.jobs.model.Response;

import java.util.List;

public interface UserService {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    Response<User> login(User user);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    Response addUser(User user);

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    Response updateUser(User user);

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    Response updatePassword(User user);

    /**
     * 查询用户信息/用户列表
     *
     * @param user
     * @return
     */
    Response<List<User>> selectUsers(User user);
}

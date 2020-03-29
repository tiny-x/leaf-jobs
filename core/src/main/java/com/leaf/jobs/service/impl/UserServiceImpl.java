package com.leaf.jobs.service.impl;

import com.google.common.base.Strings;
import com.leaf.jobs.dao.mapper.UserMapper;
import com.leaf.jobs.dao.model.User;
import com.leaf.jobs.exception.JobsException;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response login(User user) {
        User user1 = userMapper.selectByMobilePhone(user.getMobilePhone());
        if (Strings.isNullOrEmpty(user.getMobilePhone())) {
            throw new JobsException("账号不能为空");
        }
        if (Strings.isNullOrEmpty(user.getPassword())) {
            throw new JobsException("密码不能为空");
        }
        if (user1.getPassword().equals(MD5Encoder.encode(user.getPassword().getBytes()))) {
            return Response.ofSuccess();
        } else {
            throw new JobsException("用户名或密码错误");
        }
    }

    @Override
    public Response<User> selectUser(User user) throws Exception {
        List<User> users;
        if (user == null) {
            users = userMapper.selectAll();
        } else {
            users = userMapper.select(user);
        }
        return Response.ofSuccess(users);
    }

    @Override
    public Response addUser(User user) {
        userMapper.insertSelective(user);
        return Response.ofSuccess();
    }
}

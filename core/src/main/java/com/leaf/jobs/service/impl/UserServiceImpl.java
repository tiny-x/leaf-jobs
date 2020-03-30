package com.leaf.jobs.service.impl;

import com.google.common.base.Strings;
import com.leaf.jobs.context.Subjects;
import com.leaf.jobs.dao.mapper.UserMapper;
import com.leaf.jobs.dao.model.User;
import com.leaf.jobs.exception.JobsException;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response<User> login(User user) {

        Example example = Example.builder(User.class).build();
        example.and().andEqualTo("userName", user.getUserName());
        User user1 = userMapper.selectOneByExample(example);

        if (!Strings.isNullOrEmpty(user.getPassword())
                && user1.getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))) {
            return Response.ofSuccess(user1);
        } else {
            throw new JobsException("用户名或密码错误");
        }
    }

    @Override
    public Response<List<User>> selectUsers(User user) {
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

    @Override
    public Response updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return Response.ofSuccess();
    }

    @Override
    public Response updatePassword(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return Response.ofSuccess();
    }


}

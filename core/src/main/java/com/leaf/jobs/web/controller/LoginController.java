package com.leaf.jobs.web.controller;

import com.google.common.base.Strings;
import com.leaf.jobs.dao.model.User;
import com.leaf.jobs.exception.JobsException;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.UserService;
import com.leaf.jobs.web.SessionAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Response<User> login(User user, HttpSession session) {
        if (Strings.isNullOrEmpty(user.getUserName())) {
            throw new JobsException("账号不能为空");
        }
        Response<User> userResponse = userService.login(user);
        session.setAttribute(SessionAttribute.CURRENT_USER.name(), userResponse.getData());
        return Response.ofSuccess(userResponse.getData());
    }

    @RequestMapping("/logout")
    public Response logout(HttpSession session) {
        session.removeAttribute(SessionAttribute.CURRENT_USER.name());
        return Response.ofSuccess();
    }
 }

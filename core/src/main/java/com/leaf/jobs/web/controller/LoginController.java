package com.leaf.jobs.web.controller;

import com.leaf.jobs.model.Response;
import com.leaf.jobs.model.UserVo;
import com.leaf.jobs.web.SessionAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public Response login(UserVo userVo, HttpSession session) {
        session.setAttribute(SessionAttribute.CURRENT_USER.name(), userVo);
        return Response.ofSuccess();
    }

    @RequestMapping("/logout")
    public Response logout(HttpSession session) {
        session.removeAttribute(SessionAttribute.CURRENT_USER.name());
        return Response.ofSuccess();
    }
 }

package com.leaf.jobs.web.controller;

import com.google.common.base.Strings;
import com.leaf.jobs.dao.model.User;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.UserService;
import com.leaf.jobs.web.SessionAttribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/addUser")
    public Response addUser(User user) {
        return userService.addUser(user);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/updateUser")
    public Response updateUser(User user) {
        return userService.updateUser(user);
    }

    /**
     * 用户查询
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/selectUsers")
    public Response<List<User>> selectUsers(User user) {
        return userService.selectUsers(user);
    }

    /**
     * 修改自己的信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/updateSelf")
    public Response updateSelf(User user, HttpSession session) {
        User currentUser = (User) session.getAttribute(SessionAttribute.CURRENT_USER.name());
        user.setUserId(currentUser.getUserId());
        Response response = userService.updateUser(user);
        return response;
    }

    /**
     * 修改自己的密码
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/updatePassword")
    public Response updatePassword(User user, HttpSession session) {
        User currentUser = (User) session.getAttribute(SessionAttribute.CURRENT_USER.name());

        String password = DigestUtils.md5DigestAsHex(user.getOldPassword().getBytes());
        if (!currentUser.getPassword().equals(password)) {
            return Response.ofFail("密码错误！");
        }

        if (Strings.isNullOrEmpty(user.getPassword())) {
            return Response.ofFail("新密码格式错误！");
        }

        if (!user.getPassword().equals(user.getAgainPassword())) {
            return Response.ofFail("新密码两次输入不一致！");
        }
        user.setUserId(currentUser.getUserId());
        user.setPassword(password);
        Response response = userService.updatePassword(user);
        if (response.isSuccess()) {
            session.removeAttribute(SessionAttribute.CURRENT_USER.name());
        }
        return response;
    }
}

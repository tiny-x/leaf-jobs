package com.leaf.jobs.dao.mapper;

import com.leaf.jobs.dao.model.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    /**
     * 根据手机号查询用户信息
     *
     * @param mobilePhone
     * @return
     */
    User selectByMobilePhone(String mobilePhone);
}
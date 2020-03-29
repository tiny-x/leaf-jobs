package com.leaf.jobs.dao.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 手机号码
     */
    private String mobilePhone;

    private String userName;

    private String email;

    private String password;

    private String creator;

    private String updater;

    private Date createDate;

    private Date updateDate;

    private String comments;

    private static final long serialVersionUID = 1L;
}
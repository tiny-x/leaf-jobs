package com.leaf.jobs.dao.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * t_user
 * @author 
 */
@Data
@Table(name = "t_user")
public class User implements Serializable {
    /**
     * 用户ID
     */
    @Id
    private Long userId;

    /**
     * 手机号码
     */
    private String mobilePhone;

    private String userName;

    private String email;

    @Transient
    private String oldPassword;

    private String password;

    @Transient
    private String againPassword;

    private String creator;

    private String updater;

    private Date createDate;

    private Date updateDate;

    private String comments;

    private static final long serialVersionUID = 1L;
}
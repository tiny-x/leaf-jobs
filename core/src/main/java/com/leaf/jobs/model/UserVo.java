package com.leaf.jobs.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    private String userName;

    private String password;
}

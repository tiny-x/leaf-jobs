package com.leaf.jobs.dao.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * t_task_group
 * @author 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskGroup implements Serializable {
    /**
     * 服务组ID
     */
    private Long groupId;

    /**
     * 服务组
     */
    private String groupName;

    /**
     * 在线服务地址
     */
    private String onlineAddress;

    private String creator;

    private String updater;

    private Date createDate;

    private Date updateDate;

    private String comments;

    private static final long serialVersionUID = 1L;
}
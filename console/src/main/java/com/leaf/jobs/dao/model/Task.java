package com.leaf.jobs.dao.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.leaf.jobs.enums.TaskType;
import com.leaf.jobs.model.PageQuery;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * t_task
 *
 * @author
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_task")
public class Task extends PageQuery implements Serializable {

    /**
     * 服务ID
     */
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;

    /**
     * 任务组
     */
    private String taskGroup;

    /**
     * 服务名称
     */
    private String taskServiceName;

    /**
     * 服务方法名称
     */
    private String taskMethodName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 运行状态
     */
    private String taskStatus;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务脚本
     */
    private String taskScript;

    /**
     * 超时时间（毫秒）
     */
    private Long timeOut;

    /**
     * 在线服务地址
     */
    private String onlineAddress;

    /**
     * 参数
     */
    private String params;

    /**
     * cron表达式
     */
    private String cron;

    /**
     * 预警邮件
     */
    @Column(name = "RISK_EMAIL")
    private String riskEMail;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 备注
     */
    private String remark;

    private String creator;

    private String updater;

    private Date createDate;

    private Date updateDate;

    private String comments;


    private static final long serialVersionUID = 1L;
}
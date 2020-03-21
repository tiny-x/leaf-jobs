package com.leaf.jobs.dao.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.leaf.jobs.model.PageQuery;
import com.leaf.jobs.serializer.CustomDateSerializer;
import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * t_task_invoke_record
 * @author 
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_task_invoke_record")
public class TaskInvokeRecord extends PageQuery implements Serializable {

    /**
     * 记录id
     */
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long recordId;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务组
     */
    @Transient
    private String taskGroup;

    /**
     * 服务名称
     */
    @Transient
    private String taskServiceName;

    /**
     * 服务方法名称
     */
    @Transient
    private String taskMethodName;

    /**
     * 任务名称
     */
    @Transient
    private String taskName;

    /**
     * 执行时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date invokeDate;

    /**
     * 执行的机器
     */
    private String invokeIp;

    /**
     * 执行的结构 FAIL SUCCESS
     */
    private String invokeResult;

    /**
     * 完成时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date completeDate;

    /**
     * 返回
     */
    private String response;

    /**
     * 调用次数
     */
    @Transient
    private Integer count;

    /**
     * 成功次数
     */
    @Transient
    private Integer successCount;

    /**
     * 失败次数
     */
    @Transient
    private Integer errorCount;

    /**
     * 错误信息
     */
    private String stackTrace;

    private String creator;

    private String updater;

    private Date createDate;

    private Date updateDate;

    private String comments;

    private static final long serialVersionUID = 1L;
}
package com.leaf.jobs.model;

import com.google.common.base.Strings;
import lombok.*;

/**
 * 任务实体
 *
 * @author yefei
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobVo {

    /**
     * task_id, job_key
     */
    private Long Id;

    /**
     * 组/服务的名称 job_group
     */
    private String group;

    /**
     * 执行的服务
     */
    private String serviceName;

    /**
     * 方法
     */
    private String methodName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 脚本
     */
    private String taskScript;

    /**
     * 参数
     */
    private String params;

    /**
     * 超时时间
     */
    private Long timeMillis;

    /**
     * cron表达式
     */
    private String cron;

    /**
     * 预警邮件
     */
    private String riskEMail;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 任务状态
     */
    private JobStatus jobStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 取个别名 不是 jobKey，实际没有意义
     */
    public String getKey() {
        if (!Strings.isNullOrEmpty(serviceName) && !Strings.isNullOrEmpty(methodName)) {
            return serviceName + "#" + methodName;
        }
        return null;
    }
}

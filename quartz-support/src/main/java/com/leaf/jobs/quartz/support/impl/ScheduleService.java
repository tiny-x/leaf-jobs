package com.leaf.jobs.quartz.support.impl;

import com.leaf.jobs.model.JobVo;
import org.quartz.Job;
import org.quartz.SchedulerException;

/**
 * 任务的add, pause ...
 *
 * @author yefei
 */
public interface ScheduleService {

    /**
     * 添加
     *
     * @param job
     * @throws SchedulerException
     */
    void add(JobVo job, Class<? extends Job> clazz) throws SchedulerException;

    /**
     * 暂停
     *
     * @param job
     * @throws SchedulerException
     */
    void pause(JobVo job) throws SchedulerException;

    /**
     * 修改
     *
     * @throws SchedulerException
     */
    void update(JobVo job) throws SchedulerException;

    /**
     * 删除
     *
     * @throws SchedulerException
     */
    void delete(JobVo job) throws SchedulerException;

    /**
     * 触发执行一次
     *
     * @throws SchedulerException
     */
    void trigger(JobVo job) throws SchedulerException;

    /**
     * 运行任务
     *
     * @throws SchedulerException
     */
    void resumeJob(JobVo job) throws SchedulerException;
}

package com.leaf.jobs.quartz.support.impl;

import com.leaf.jobs.model.JobVo;
import com.leaf.jobs.model.exception.JobsException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * quartz schedule
 */
@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    /**
     * springboot autoconfig
     */
    @Autowired
    private Scheduler scheduler;

    @Override
    public void add(JobVo job, Class<? extends Job> clazz) throws SchedulerException {

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(String.valueOf(job.getId()), job.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();

        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(String.valueOf(job.getId()), job.getGroup())
                .build();

        Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
        log.info("add job, group:{}, name:{}, date:{}", job.getGroup(), String.valueOf(job.getId()), date);
    }

    @Override
    public void pause(JobVo job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(String.valueOf(job.getId()), job.getGroup());
        if (!scheduler.checkExists(jobKey)) {
            throw new JobsException(jobKey + "is not exists!");
        }
        scheduler.pauseJob(jobKey);
        log.info("pause job, group:{}, name:{}", job.getGroup(), String.valueOf(job.getId()));
    }

    @Override
    public void update(JobVo job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(String.valueOf(job.getId()), job.getGroup());
        JobKey jobKey = JobKey.jobKey(String.valueOf(job.getId()), job.getGroup());

        if (!scheduler.checkExists(triggerKey)) {
            throw new JobsException(triggerKey + "is not exists!");
        }

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();

        Set<Trigger> triggers = new HashSet<>();
        triggers.add(cronTrigger);
        scheduler.scheduleJob(scheduler.getJobDetail(jobKey), triggers, true);
        log.info("update job, group:{}, name:{}", job.getGroup(), String.valueOf(job.getId()));
    }

    @Override
    public void delete(JobVo job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(String.valueOf(job.getId()), job.getGroup());

        if (!scheduler.checkExists(triggerKey)) {
            throw new JobsException(triggerKey + "is not exists!");
        }
        scheduler.unscheduleJob(triggerKey);
        log.info("delete job, group:{}, name:{}", job.getGroup(), String.valueOf(job.getId()));
    }

    @Override
    public void trigger(JobVo job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(String.valueOf(job.getId()), job.getGroup());

        if (!scheduler.checkExists(jobKey)) {
            throw new JobsException(jobKey + " is not exists!");
        }
        scheduler.triggerJob(jobKey);
        log.info("trigger job, group:{}, name:{}", job.getGroup(), String.valueOf(job.getId()));
    }

    @Override
    public void resumeJob(JobVo job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(String.valueOf(job.getId()), job.getGroup());

        if (!scheduler.checkExists(jobKey)) {
            throw new JobsException(jobKey + "is not exists!");
        }
        scheduler.resumeJob(jobKey);
        log.info("resume job, group:{}, name:{}", job.getGroup(), String.valueOf(job.getId()));
    }

}

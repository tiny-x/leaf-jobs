package com.leaf.jobs;

import com.leaf.jobs.job.RpcTimerJob;
import com.leaf.jobs.model.JobVo;
import com.leaf.jobs.quartz.support.impl.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JobsApplication.class})// 指定启动类
@Slf4j
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void addJob() throws Exception {
        JobVo jobVo = JobVo.builder()
                .group("core")
                .serviceName("com.leaf.core.MailService#sendMail()")
                .cron("1/2 * * * * ? ")
                .build();

        scheduleService.add(jobVo, RpcTimerJob.class);
    }

    @Test
    public void pause() throws Exception {
        JobVo jobVo = JobVo.builder()
                .group("core")
                .serviceName("com.leaf.core.MailService#sendMail()")
                .build();

        scheduleService.pause(jobVo);
    }

}

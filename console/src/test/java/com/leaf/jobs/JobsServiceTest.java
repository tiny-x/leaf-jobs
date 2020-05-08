package com.leaf.jobs;

import com.leaf.jobs.model.JobStatus;
import com.leaf.jobs.model.JobVo;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.JobsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JobsApplication.class})// 指定启动类
@Slf4j
public class JobsServiceTest {

    @Autowired
    private JobsService jobsService;

    @Test
    public void add() throws Exception {
        JobVo jobVo = new JobVo();
        jobVo.setGroup("rjb");
        jobVo.setServiceName("com.leaf.jobs.HelloService");
        jobVo.setMethodName("sayHello");
        jobVo.setCron("1/15 * * * * ?");
        jobVo.setTimeMillis(2000L);

        Response services = jobsService.addJob(jobVo);
        System.in.read();
    }

    @Test
    public void updateJobStatus() throws Exception {
        JobVo jobVo = new JobVo();
        jobVo.setGroup("rjb");
        jobVo.setServiceName("com.leaf.jobs.HelloService");
        jobVo.setJobStatus(JobStatus.PAUSE);
        Response services = jobsService.updateJobStatus(jobVo);
        System.in.read();
    }

}

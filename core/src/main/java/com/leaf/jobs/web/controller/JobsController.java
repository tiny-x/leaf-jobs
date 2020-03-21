package com.leaf.jobs.web.controller;

import com.leaf.jobs.dao.model.Task;
import com.leaf.jobs.model.JobVo;
import com.leaf.jobs.model.RegisterServiceVo;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.JobsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/task")
public class JobsController {

    @Autowired
    private JobsService jobsService;

    /**
     * 所有的组信息
     *
     * @return
     */
    @RequestMapping("/services")
    public Response<Set<RegisterServiceVo>> groups(JobVo jobVo) {
        return jobsService.getRegisterService(jobVo);
    }

    /**
     * 添加任务
     *
     * @param jobVo
     * @return
     */
    @RequestMapping("/addJob")
    public Response addJob(JobVo jobVo) throws Exception {
        return jobsService.addJob(jobVo);
    }

    /**
     * 任务状态
     *
     * @param jobVo
     * @return
     */
    @RequestMapping("/updateJobStatus")
    public Response updateJobStatus(JobVo jobVo) throws Exception {
        return jobsService.updateJobStatus(jobVo);
    }

    /**
     * 任务状态
     *
     * @param jobVo
     * @return
     */
    @RequestMapping("/trigger")
    public Response trigger(JobVo jobVo) throws Exception {
        return jobsService.trigger(jobVo);
    }

    /**
     * 删除任务
     *
     * @param jobVo
     * @return
     */
    @RequestMapping("/delete")
    public Response delete(JobVo jobVo) throws Exception {
        return jobsService.delete(jobVo);
    }

    /**
     * 更新任务
     *
     * @param jobVo
     * @return
     */
    @RequestMapping("/update")
    public Response update(JobVo jobVo) throws Exception {
        return jobsService.update(jobVo);
    }

    /**
     * 查询任务
     *
     * @param task
     * @return
     */
    @RequestMapping("/selectTask")
    public Response<List<Task>> selectTask(Task task) throws Exception {
        return jobsService.selectTask(task);
    }
}

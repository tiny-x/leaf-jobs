package com.leaf.jobs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import com.leaf.common.concurrent.ConcurrentSet;
import com.leaf.jobs.context.JobsContext;
import com.leaf.jobs.dao.mapper.TaskMapper;
import com.leaf.jobs.dao.model.Task;
import com.leaf.jobs.job.RpcTimerJob;
import com.leaf.jobs.model.JobStatus;
import com.leaf.jobs.model.JobVo;
import com.leaf.jobs.model.RegisterServiceVo;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.generate.SnowflakeIdWorker;
import com.leaf.jobs.quartz.support.impl.ScheduleService;
import com.leaf.jobs.service.JobsService;
import com.leaf.register.api.model.RegisterMeta;
import com.leaf.rpc.consumer.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

import static com.leaf.jobs.integration.rpc.GenericInvokeInitListener.initInvoke;

/**
 * @author yefei
 */
@Slf4j
@Service
public class JobsServiceImpl implements JobsService {

    @Autowired
    private Consumer consumer;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public Response<Set<RegisterServiceVo>> getRegisterService(JobVo jobVo) {
        Set<RegisterServiceVo> serviceVos = JobsContext.getRegisterServiceVo(jobVo.getGroup(), jobVo.getServiceName());
        return Response.ofSuccess(serviceVos);
    }

    @Override
    public Response addJob(JobVo jobVo) throws Exception {

        ConcurrentSet<RegisterMeta> registerMetas = JobsContext.getGroupMap().get(jobVo.getGroup());
        if (registerMetas == null) {
            Response.ofFail("任务已经下线，请重新刷新试试");
        }
        String onlineIp = "";
        for (RegisterMeta registerMeta : registerMetas) {
            if (registerMeta.getServiceMeta().getServiceProviderName().equals(jobVo.getServiceName())) {
                onlineIp = onlineIp + "," + registerMeta.getAddress();
            }
        }
        onlineIp = onlineIp.replaceFirst(",", "");

        Task task = Task.builder().taskGroup(jobVo.getGroup())
                .taskId(SnowflakeIdWorker.getInstance().nextId())
                .taskGroup(jobVo.getGroup())
                .taskServiceName(jobVo.getServiceName())
                .taskMethodName(jobVo.getMethodName())
                .taskName(jobVo.getTaskName())
                .taskStatus(JobStatus.RUN.name())
                .params(jobVo.getParams())
                .cron(jobVo.getCron())
                .principal(jobVo.getPrincipal())
                .riskEMail(jobVo.getRiskEMail())
                .remark(jobVo.getRemark())
                .onlineAddress(onlineIp)
                .timeOut(jobVo.getTimeMillis()).build();
        jobVo.setId(task.getTaskId());

        taskMapper.insert(task);
        scheduleService.add(jobVo, RpcTimerJob.class);
        initInvoke(task.getTaskId(), jobVo.getGroup(), jobVo.getServiceName(), consumer, jobVo.getTimeMillis());

        return Response.ofSuccess();
    }

    @Override
    public Response<List<Task>> selectTask(Task task) {
        Page page = PageHelper.startPage(task.getPage(), task.getLimit());

        Example example = new Example(Task.class);
        if (!Strings.isNullOrEmpty(task.getTaskGroup())) {
            example.and().andEqualTo("taskGroup", task.getTaskGroup());
        }
        if (!Strings.isNullOrEmpty(task.getTaskServiceName())) {
            example.and().andLike("taskServiceName", "%" + task.getTaskServiceName() + "%");
        }
        if (!Strings.isNullOrEmpty(task.getTaskName())) {
            example.and().andLike("taskName", task.getTaskName() + "%");
        }

        List<Task> tasks = taskMapper.selectByExample(example);
        Response response = Response.ofSuccess(tasks);
        response.setCount(page.getTotal());
        return response;
    }

    @Override
    public Response update(JobVo jobVo) throws Exception {
        Task task = Task.builder().taskId(jobVo.getId())
                .taskGroup(jobVo.getGroup())
                .taskId(SnowflakeIdWorker.getInstance().nextId())
                .taskGroup(jobVo.getGroup())
                .taskServiceName(jobVo.getServiceName())
                .taskMethodName(jobVo.getMethodName())
                .taskName(jobVo.getTaskName())
                .taskStatus(JobStatus.RUN.name())
                .params(jobVo.getParams())
                .cron(jobVo.getCron())
                .principal(jobVo.getPrincipal())
                .riskEMail(jobVo.getRiskEMail())
                .remark(jobVo.getRemark())
                .timeOut(jobVo.getTimeMillis()).build();
        taskMapper.updateByPrimaryKeySelective(task);
        scheduleService.update(jobVo);
        return Response.ofSuccess();
    }

    @Override
    public Response delete(JobVo jobVo) throws Exception {
        scheduleService.delete(jobVo);
        taskMapper.deleteByPrimaryKey(jobVo.getId());
        return Response.ofSuccess();
    }

    @Override
    public Response updateJobStatus(JobVo jobVo) throws Exception {
        switch (jobVo.getJobStatus()) {
            case RUN:
                scheduleService.resumeJob(jobVo);
                break;
            case PAUSE:
                scheduleService.pause(jobVo);
                break;
        }
        Task task = Task.builder()
                .taskId(jobVo.getId())
                .taskStatus(jobVo.getJobStatus().name())
                .build();

        taskMapper.updateByPrimaryKeySelective(task);
        return Response.ofSuccess();
    }

    @Override
    public Response trigger(JobVo jobVo) throws Exception {

        scheduleService.trigger(jobVo);
        return Response.ofSuccess();
    }
}


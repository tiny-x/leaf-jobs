package com.leaf.jobs.service;

import com.leaf.jobs.dao.model.Task;
import com.leaf.jobs.model.JobVo;
import com.leaf.jobs.model.RegisterServiceVo;
import com.leaf.jobs.model.Response;

import java.util.List;
import java.util.Set;

/**
 * @author yefei
 */
public interface JobsService {

    /**
     * 注册的服务信息
     *
     * @return
     */
    Response<Set<RegisterServiceVo>> getRegisterService(JobVo jobVo);

    /**
     * 添加任务
     *
     * @param jobVo
     * @return
     */
    Response addJob(JobVo jobVo) throws Exception;

    /**
     * 更新任务
     *
     * @param jobVo
     * @return
     */
    Response update(JobVo jobVo) throws Exception ;

    /**
     * 删除任务
     *
     * @param jobVo
     * @return
     */
    Response delete(JobVo jobVo) throws Exception;

    /**
     * 暂停任务
     * 运行任务
     *
     * @param jobVo
     * @return
     */
    Response updateJobStatus(JobVo jobVo) throws Exception;

    /**
     * 触发一次
     *
     * @param jobVo
     * @return
     * @throws Exception
     */
    Response trigger(JobVo jobVo) throws Exception;

    /**
     * 查询添加任务列表
     *
     * @return
     * @throws Exception
     */
    Response<List<Task>> selectTask(Task task) throws Exception;

}

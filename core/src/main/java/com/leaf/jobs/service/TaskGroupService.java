package com.leaf.jobs.service;

import com.leaf.jobs.dao.model.TaskGroup;
import com.leaf.jobs.model.Response;

import java.util.List;

public interface TaskGroupService {

    /**
     * 查询任务组
     *
     * @param taskGroup
     * @return
     */
    Response<List<TaskGroup>> selectGroup(TaskGroup taskGroup);
}

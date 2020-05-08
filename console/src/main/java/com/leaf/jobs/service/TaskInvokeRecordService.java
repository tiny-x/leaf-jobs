package com.leaf.jobs.service;

import com.leaf.jobs.dao.model.TaskInvokeRecord;
import com.leaf.jobs.model.Response;

public interface TaskInvokeRecordService {

    /**
     * 查询执行记录
     *
     * @param taskInvokeRecord
     * @return
     */
    Response<TaskInvokeRecord> selectInvokeRecord(TaskInvokeRecord taskInvokeRecord);
}

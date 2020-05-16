package com.leaf.jobs.service;

import com.leaf.jobs.dao.model.TaskInvokeRecord;
import com.leaf.jobs.model.Response;

import java.util.List;

public interface TaskInvokeRecordService {

    /**
     * 查询执行记录
     *
     * @param taskInvokeRecord
     * @return
     */
    Response<List<TaskInvokeRecord>> selectInvokeRecord(TaskInvokeRecord taskInvokeRecord);

    /**
     * 查询执行记录详情
     *
     * @param taskInvokeRecord
     * @return
     */
    Response<TaskInvokeRecord> selectInvokeRecordDetail(TaskInvokeRecord taskInvokeRecord);
}

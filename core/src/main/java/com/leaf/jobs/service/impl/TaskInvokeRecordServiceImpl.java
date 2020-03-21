package com.leaf.jobs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leaf.jobs.dao.mapper.TaskInvokeRecordMapper;
import com.leaf.jobs.dao.model.TaskInvokeRecord;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.TaskInvokeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务执行记录
 */
@Service
@Slf4j
public class TaskInvokeRecordServiceImpl implements TaskInvokeRecordService {

    @Autowired
    private TaskInvokeRecordMapper taskInvokeRecordMapper;

    @Override
    public Response<TaskInvokeRecord> selectInvokeRecord(TaskInvokeRecord taskInvokeRecord) {

        Page page= PageHelper.startPage(taskInvokeRecord.getPage(), taskInvokeRecord.getLimit());
        List<TaskInvokeRecord> taskInvokeRecords = taskInvokeRecordMapper.selectTaskInvokeRecord(taskInvokeRecord);

        Response response = Response.ofSuccess(taskInvokeRecords);
        response.setCount(page.getTotal());
        return response;
    }
}

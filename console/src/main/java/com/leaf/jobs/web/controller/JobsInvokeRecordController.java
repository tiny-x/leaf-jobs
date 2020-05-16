package com.leaf.jobs.web.controller;

import com.leaf.jobs.dao.model.TaskInvokeRecord;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.TaskInvokeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yefei
 */
@Slf4j
@RestController
@RequestMapping("/invokeRecord")
public class JobsInvokeRecordController {

    @Autowired
    private TaskInvokeRecordService taskInvokeRecordService;

    @RequestMapping("/selectInvokeRecord")
    public Response<List<TaskInvokeRecord>> selectInvokeRecord(TaskInvokeRecord taskInvokeRecord) {
        Response<List<TaskInvokeRecord>> taskInvokeRecordResponse = taskInvokeRecordService.selectInvokeRecord(taskInvokeRecord);
        return taskInvokeRecordResponse;
    }

    @RequestMapping("/selectInvokeRecordDetail")
    public Response<TaskInvokeRecord> selectInvokeRecordDetail(TaskInvokeRecord taskInvokeRecord) {
        Response<TaskInvokeRecord> taskInvokeRecordResponse = taskInvokeRecordService.selectInvokeRecordDetail(taskInvokeRecord);
        return taskInvokeRecordResponse;
    }
}

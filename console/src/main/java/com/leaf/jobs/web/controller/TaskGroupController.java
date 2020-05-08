package com.leaf.jobs.web.controller;

import com.leaf.jobs.dao.model.TaskGroup;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.TaskGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskGroupController {

    @Autowired
    private TaskGroupService taskGroupService;

    @RequestMapping("/task/selectGroup")
    public Response<List<TaskGroup>> selectGroup(TaskGroup taskGroup) {
        return taskGroupService.selectGroup(taskGroup);
    }
}

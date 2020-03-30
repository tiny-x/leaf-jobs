package com.leaf.jobs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import com.leaf.jobs.dao.mapper.TaskGroupMapper;
import com.leaf.jobs.dao.model.TaskGroup;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.service.TaskGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class TaskGroupServiceImpl implements TaskGroupService {

    @Autowired
    private TaskGroupMapper taskGroupMapper;

    @Override
    public Response<List<TaskGroup>> selectGroup(TaskGroup taskGroup) {
        Page page = PageHelper.startPage(taskGroup.getPage(), taskGroup.getLimit());
        Example example = Example.builder(TaskGroup.class).build();

        if (!Strings.isNullOrEmpty(taskGroup.getGroupName())) {
            example.and().andEqualTo("groupName", taskGroup.getGroupName());
        }

        List<TaskGroup> taskGroups = taskGroupMapper.selectByExample(example);
        Response response = Response.ofSuccess(taskGroups);
        response.setCount(page.getTotal());
        return response;
    }
}

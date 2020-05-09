package com.leaf.jobs.support.service;

import com.leaf.jobs.enums.TaskType;
import com.leaf.jobs.model.Invocation;
import com.leaf.jobs.ScriptInvokeService;

public class ScriptInvokeServiceImpl implements ScriptInvokeService {

    @Override
    public Invocation invoke(Invocation invocation) {
        TaskType taskType = TaskType.match(invocation.getTaskTye());
        switch (taskType) {
            case SHELL:
            case GROOVY:
            case SERVICE:
            default:
        }

        return null;
    }
}

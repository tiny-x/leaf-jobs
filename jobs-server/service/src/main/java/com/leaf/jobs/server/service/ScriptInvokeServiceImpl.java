package com.leaf.jobs.server.service;

import com.leaf.jobs.ScriptInvokeService;
import com.leaf.jobs.model.Invocation;
import com.leaf.jobs.server.service.strategy.ScriptInvokeStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yefei
 */
public class ScriptInvokeServiceImpl implements ScriptInvokeService {

    @Autowired
    private ScriptInvokeStrategyContext scriptInvokeStrategyContext;

    @Override
    public Invocation invoke(Invocation invocation) {
        return scriptInvokeStrategyContext.invoke(invocation);
    }
}

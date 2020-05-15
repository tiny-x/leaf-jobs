package com.leaf.jobs.server.service.strategy;

import com.leaf.jobs.enums.TaskType;
import com.leaf.jobs.model.Invocation;
import com.leaf.jobs.server.service.Strategy;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yefei
 */
@Slf4j
@Strategy(TaskType.GROOVY)
public class GroovyScriptInvokeStrategy implements ScriptInvokeStrategy {

    @Override
    public Invocation invoke(Invocation invocation) {
        GroovyShell shell = new GroovyShell();
        Object evaluate = null;
        try {
            evaluate = shell.evaluate(invocation.getScript());
        } catch (Exception e) {
            log.error("groovy script invoke fail, script: {}", invocation.getScript(), e);
        } finally {
            shell.getClassLoader().clearCache();
        }
        invocation.setResult(evaluate.toString());
        return invocation;
    }
}

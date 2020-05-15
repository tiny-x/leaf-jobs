package com.leaf.jobs.server.service.strategy;

import com.leaf.jobs.ScriptInvokeService;
import com.leaf.jobs.enums.TaskType;
import com.leaf.jobs.model.Invocation;
import com.leaf.jobs.server.service.Strategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yefei
 */
@Component
public class ScriptInvokeStrategyContext implements BeanPostProcessor, ScriptInvokeService {

    private Map<TaskType, ScriptInvokeStrategy> strategies = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ScriptInvokeStrategy) {
            Strategy annotation = bean.getClass().getAnnotation(Strategy.class);
            TaskType type = annotation.value();
            strategies.put(type, (ScriptInvokeStrategy) bean);
        }
        return bean;
    }

    @Override
    public Invocation invoke(Invocation invocation) {
        TaskType taskType = TaskType.match(invocation.getTaskTye());
        ScriptInvokeStrategy scriptInvokeStrategy = strategies.get(taskType);
        if (scriptInvokeStrategy == null) {
            throw new RuntimeException("no script strategy: " + taskType);
        } else {
            return scriptInvokeStrategy.invoke(invocation);
        }
    }
}

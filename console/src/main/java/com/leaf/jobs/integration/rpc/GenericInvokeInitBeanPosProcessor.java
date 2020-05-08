package com.leaf.jobs.integration.rpc;

import com.leaf.common.model.ServiceMeta;
import com.leaf.jobs.context.JobsContext;
import com.leaf.jobs.dao.mapper.TaskMapper;
import com.leaf.jobs.dao.model.Task;
import com.leaf.rpc.GenericProxyFactory;
import com.leaf.rpc.consumer.Consumer;
import com.leaf.rpc.consumer.InvokeType;
import com.leaf.rpc.consumer.invoke.GenericInvoke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化GenericInvoke
 *
 * @author yefei
 */
@Component
@Slf4j
public class GenericInvokeInitBeanPosProcessor implements BeanPostProcessor {

    @Autowired
    private Consumer consumer;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TaskMapper) {
            TaskMapper taskMapper = (TaskMapper) bean;
            List<Task> tasks = taskMapper.selectAll();
            tasks.forEach(task -> initInvoke(task.getTaskId(), task.getTaskGroup(), task.getTaskServiceName(), task.getTimeOut()));
        }
        return bean;
    }

    public void initInvoke(Long taskId, String group, String serviceName, Long timeMills) {
        ServiceMeta serviceMeta = new ServiceMeta(group, serviceName);
        GenericInvoke genericInvoke = GenericProxyFactory.factory()
                .consumer(consumer)
                .directory(serviceMeta)
                .timeMillis(timeMills)
                .invokeType(InvokeType.ASYNC)
                .newProxy();
        JobsContext.getInvokeMap().put(taskId, genericInvoke);
        log.info("添加执行器taskId: {}, group: {}, serviceName: {}, timeMills: {}", taskId, group, serviceName, timeMills);
    }
}

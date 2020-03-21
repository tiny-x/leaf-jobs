package com.leaf.jobs.integration.rpc;

import com.leaf.common.model.ServiceMeta;
import com.leaf.jobs.context.JobsContext;
import com.leaf.jobs.dao.mapper.TaskMapper;
import com.leaf.jobs.dao.model.Task;
import com.leaf.rpc.GenericProxyFactory;
import com.leaf.rpc.consumer.Consumer;
import com.leaf.rpc.consumer.InvokeType;
import com.leaf.rpc.consumer.invoke.GenericInvoke;
import com.leaf.serialization.api.SerializerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * spring 容器监听
 * 初始化GenericInvoke
 *
 * @author yefei
 */
@Component
@Slf4j
public class GenericInvokeInitListener {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private Consumer consumer;

    @EventListener
    public void genericInvokeInit(ContextRefreshedEvent event) {
        List<Task> tasks = taskMapper.selectAll();
        for (Task task : tasks) {
            initInvoke(task.getTaskId(), task.getTaskGroup(), task.getTaskServiceName(), consumer, task.getTimeOut());
        }
    }

    public static void initInvoke(Long taskId, String group, String serviceName, Consumer consumer, Long timeMills) {
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

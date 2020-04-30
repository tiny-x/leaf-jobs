package com.leaf.jobs.integration.rpc;

import com.leaf.common.model.ServiceMeta;
import com.leaf.jobs.context.JobsContext;
import com.leaf.jobs.dao.mapper.TaskGroupMapper;
import com.leaf.jobs.dao.mapper.TaskMapper;
import com.leaf.jobs.dao.model.Task;
import com.leaf.jobs.dao.model.TaskGroup;
import com.leaf.jobs.model.JobsConstants;
import com.leaf.rpc.GenericProxyFactory;
import com.leaf.rpc.consumer.Consumer;
import com.leaf.rpc.consumer.InvokeType;
import com.leaf.rpc.consumer.invoke.GenericInvoke;
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
    private TaskGroupMapper taskGroupMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private Consumer consumer;

    @EventListener
    public void genericInvokeInit(ContextRefreshedEvent event) {
        List<Task> tasks = taskMapper.selectAll();
        for (Task task : tasks) {
            initInvoke(task.getTaskId(), task.getTaskGroup(), task.getTaskServiceName(), task.getTimeOut());
            int i = taskGroupMapper.updateOnlineAddress(task.getTaskGroup(), task.getOnlineAddress());
            if (i == 0) {
                TaskGroup taskGroup = TaskGroup.builder()
                        .groupName(task.getTaskGroup())
                        .onlineAddress(task.getOnlineAddress())
                        .creator(JobsConstants.DEFAULT_CREATOR)
                        .updater(JobsConstants.DEFAULT_UPDATER)
                        .build();
                taskGroupMapper.insertSelective(taskGroup);
            }
        }
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

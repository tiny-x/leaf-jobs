package com.leaf.jobs.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Strings;
import com.leaf.common.context.RpcContext;
import com.leaf.common.model.ServiceMeta;
import com.leaf.jobs.constants.JobsConstants;
import com.leaf.jobs.context.JobsContext;
import com.leaf.jobs.dao.mapper.TaskInvokeRecordMapper;
import com.leaf.jobs.dao.mapper.TaskMapper;
import com.leaf.jobs.dao.model.Task;
import com.leaf.jobs.dao.model.TaskInvokeRecord;
import com.leaf.jobs.enums.InvokeResult;
import com.leaf.jobs.enums.TaskType;
import com.leaf.jobs.generate.SnowflakeIdWorker;
import com.leaf.jobs.model.Invocation;
import com.leaf.jobs.utils.StackTraceUtil;
import com.leaf.remoting.api.exception.RemotingTimeoutException;
import com.leaf.rpc.consumer.future.InvokeFutureContext;
import com.leaf.rpc.consumer.future.InvokeFutureListener;
import com.leaf.rpc.consumer.invoke.GenericInvoke;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * quartz最终出发调用的方法
 * com.leaf.jobs.job.RpcTimerJob#execute(org.quartz.JobExecutionContext)
 *
 * @author yefei
 */
@Slf4j
@DisallowConcurrentExecution
public class RpcTimerJob implements Job {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();

        TaskMapper taskMapper = JobsContext.getApplicationContext().getBean(TaskMapper.class);
        Task task = taskMapper.selectByPrimaryKey(key.getName());
        String taskType = task.getTaskType();
        TaskType match = TaskType.match(taskType);

        ServiceMeta serviceMeta = new ServiceMeta(task.getTaskGroup(), task.getTaskServiceName());
        switch (match) {
            case SERVICE:
                String[] params = null;
                if (!Strings.isNullOrEmpty(task.getParams())) {
                    params = task.getParams().split(",");
                }
                serviceTask(task, serviceMeta, params);
                break;
            case GROOVY:
            case SHELL:
                Invocation invocation = new Invocation();
                invocation.setTaskTye(match.name());
                invocation.setScript(task.getTaskScript());
                RpcContext.setTimeout(task.getTimeOut());
                serviceTask(task, serviceMeta, invocation);
                break;
        }

    }

    private void serviceTask(Task task, ServiceMeta serviceMeta, Object... params) {
        TaskInvokeRecordMapper taskInvokeRecordMapper = JobsContext.getApplicationContext().getBean(TaskInvokeRecordMapper.class);
        TaskInvokeRecord taskInvokeRecord = TaskInvokeRecord
                .builder()
                .recordId(SnowflakeIdWorker.getInstance().nextId())
                .invokeResult(InvokeResult.INVOKING.getCode())
                .taskId(task.getTaskId())
                .invokeDate(new Date())
                .build();
        try {
            taskInvokeRecordMapper.insertSelective(taskInvokeRecord);
            RpcContext.putAttachment(JobsConstants.RECORD_ID_ATTACH_KEY, String.valueOf(taskInvokeRecord.getRecordId()));

            GenericInvoke invoke = JobsContext.getInvoke(task.getTaskId());
            checkNotNull(invoke, "任务调度失败 invoke is null, taskId: %s serviceMeat: %s", task.getTaskId(), serviceMeta);

            log.info("执行任务，serviceMeta: {}, method: {}, params: {}", serviceMeta, task.getTaskMethodName(), params);
            invoke.$invoke(task.getTaskMethodName(), params);
            log.info("任务执行成功，serviceMeta: {}, method: {}, params: {}", serviceMeta, task.getTaskMethodName(), params);

            InvokeFutureContext.getInvokeFuture().addListener(new InvokeFutureListener() {
                @Override
                public void complete(Object o) {
                    log.info("任务调度成功，serviceMeta: {}, method: {}",
                            serviceMeta,
                            task.getTaskMethodName());
                    taskInvokeRecord.setCompleteDate(new Date());
                    taskInvokeRecord.setInvokeResult(InvokeResult.INVOKE_SUCCESS.getCode());
                    taskInvokeRecord.setResponse(o.toString());
                    taskInvokeRecordMapper.updateByPrimaryKeySelective(taskInvokeRecord);
                }

                @Override
                public void failure(Throwable throwable) {
                    log.error("任务调度失败，serviceMeta: {}, method: {}",
                            serviceMeta,
                            task.getTaskMethodName(),
                            throwable);
                    String errorMessage = StackTraceUtil.stackTrace(throwable);
                    if (errorMessage.length() > 512) {
                        errorMessage = errorMessage.substring(0, 512);
                    }
                    if (throwable instanceof RemotingTimeoutException) {
                        taskInvokeRecord.setInvokeResult(InvokeResult.INVOKE_TIME_OUT.getCode());
                    } else {
                        taskInvokeRecord.setInvokeResult(InvokeResult.INVOKE_FAIL.getCode());
                    }
                    taskInvokeRecord.setStackTrace(errorMessage);
                    taskInvokeRecordMapper.updateByPrimaryKeySelective(taskInvokeRecord);
                }
            });
        } catch (Throwable throwable) {
            log.error("任务调度失败，serviceMeta: {}, method: {}, params: {}",
                    serviceMeta,
                    task.getTaskMethodName(),
                    params,
                    throwable);
            String errorMessage = StackTraceUtil.stackTrace(throwable);
            if (errorMessage.length() > 512) {
                errorMessage = errorMessage.substring(0, 512);
            }
            taskInvokeRecord.setInvokeResult(InvokeResult.SCHEDULE_FAIL.getCode());
            taskInvokeRecord.setStackTrace(errorMessage);
            taskInvokeRecordMapper.updateByPrimaryKeySelective(taskInvokeRecord);
        }
    }
}
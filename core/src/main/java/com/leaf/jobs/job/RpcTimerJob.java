package com.leaf.jobs.job;

import com.google.common.base.Strings;
import com.leaf.common.model.ServiceMeta;
import com.leaf.jobs.context.JobsContext;
import com.leaf.jobs.dao.mapper.TaskInvokeRecordMapper;
import com.leaf.jobs.dao.mapper.TaskMapper;
import com.leaf.jobs.dao.model.Task;
import com.leaf.jobs.dao.model.TaskInvokeRecord;
import com.leaf.jobs.enums.InvokeResult;
import com.leaf.jobs.model.id.generate.SnowflakeIdWorker;
import com.leaf.jobs.model.utils.StackTraceUtil;
import com.leaf.rpc.consumer.future.InvokeFutureContext;
import com.leaf.rpc.consumer.future.InvokeFutureListener;
import com.leaf.rpc.consumer.invoke.GenericInvoke;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * quartz最终出发调用的方法
 * com.leaf.jobs.job.RpcTimerJob#execute(org.quartz.JobExecutionContext)
 *
 * @author yefei
 */
@DisallowConcurrentExecution
public class RpcTimerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(RpcTimerJob.class);

    /**
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();

        TaskMapper taskMapper = JobsContext.getApplicationContext().getBean(TaskMapper.class);
        Task task = taskMapper.selectByPrimaryKey(key.getName());

        String serviceName = task.getTaskServiceName();
        String methodName = task.getTaskMethodName();

        ServiceMeta serviceMeta = new ServiceMeta(task.getTaskGroup(), serviceName);

        GenericInvoke invoke = JobsContext.getInvoke(task.getTaskId());

        String[] params = null;
        if (!Strings.isNullOrEmpty(task.getParams())) {
            params = task.getParams().split(",");
        }
        TaskInvokeRecordMapper taskInvokeRecordMapper = JobsContext.getApplicationContext().getBean(TaskInvokeRecordMapper.class);
        TaskInvokeRecord taskInvokeRecord = TaskInvokeRecord
                .builder()
                .recordId(SnowflakeIdWorker.getInstance().nextId())
                .taskId(task.getTaskId())
                .invokeDate(new Date())
                .build();
        taskInvokeRecordMapper.insertSelective(taskInvokeRecord);

        try {
            checkNotNull(invoke, "任务调度失败 invoke is null, taskId: %s serviceMeat: %s", task.getTaskId(), serviceMeta);

            logger.error("执行任务，serviceMeta: {}, method: {}, params: {}", serviceMeta, methodName, params);
            invoke.$invoke(methodName, params);
            logger.info("任务执行成功，serviceMeta: {}, method: {}, params: {}", serviceMeta, methodName, params);


            InvokeFutureContext.getInvokeFuture().addListener(new InvokeFutureListener() {
                @Override
                public void complete(Object o) {
                    logger.info("任务调度成功，serviceMeta: {}, method: {}",
                            serviceMeta,
                            methodName);
                    taskInvokeRecord.setCompleteDate(new Date());
                    taskInvokeRecord.setInvokeResult(InvokeResult.INVOKE_SUCCESS.getCode());
                    taskInvokeRecord.setResponse(o.toString());
                    taskInvokeRecordMapper.updateByPrimaryKeySelective(taskInvokeRecord);
                }

                @Override
                public void failure(Throwable throwable) {
                    logger.error("任务调度失败，serviceMeta: {}, method: {}",
                            serviceMeta,
                            methodName,
                            throwable);
                    String errorMessage = StackTraceUtil.stackTrace(throwable);
                    if (errorMessage.length() > 512) {
                        errorMessage = errorMessage.substring(0, 512);
                    }
                    taskInvokeRecord.setInvokeResult(InvokeResult.INVOKE_FAIL.getCode());
                    taskInvokeRecord.setStackTrace(errorMessage);
                    taskInvokeRecordMapper.updateByPrimaryKeySelective(taskInvokeRecord);
                }
            });
        } catch (Throwable throwable) {
            logger.error("任务调度失败，serviceMeta: {}, method: {}, params: {}",
                    serviceMeta,
                    methodName,
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
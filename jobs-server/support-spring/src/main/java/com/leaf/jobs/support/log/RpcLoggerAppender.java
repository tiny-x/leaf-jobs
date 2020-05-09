package com.leaf.jobs.support.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.StatusManager;
import com.google.common.base.Preconditions;
import com.leaf.common.context.RpcContext;
import com.leaf.jobs.constants.JobsConstants;
import com.leaf.jobs.LogsProvider;
import com.leaf.jobs.model.LogsDTO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RpcLoggerAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static LogsProvider logsProvider;

    public static void setLogsProvider(LogsProvider logsProvider) {
        RpcLoggerAppender.logsProvider = logsProvider;
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        String content = iLoggingEvent.getFormattedMessage();
        Preconditions.checkArgument(logsProvider !=  null, "logsProvider is null");
        logsProvider.transfer(new LogsDTO()
                .setRecordId(Long.valueOf(RpcContext.getAttachment(JobsConstants.RECORD_ID_ATTACH_KEY)))
                .setContent(content)
                .setLevel(iLoggingEvent.getLevel().toString())
                .setLogName(iLoggingEvent.getLoggerName())
                .setThreadName(iLoggingEvent.getThreadName())
                .setStackTraceElements(iLoggingEvent.getCallerData())
        );
    }

    public static void configure(LoggerContext lc, Class<?> clazz) {
        StatusManager sm = lc.getStatusManager();
        if (sm != null) {
            sm.add(new InfoStatus("Setting up default configuration.", lc));
        }

        // 走rpc 输出到控制台
        RpcLoggerAppender rpcLoggerAppender = new RpcLoggerAppender();
        rpcLoggerAppender.setContext(lc);
        rpcLoggerAppender.setName(JobsConstants.RPC_LOG_APPENDER_NAME);
        rpcLoggerAppender.start();

        ch.qos.logback.classic.Logger logger = lc.getLogger(clazz);
        logger.setAdditive(true);
        logger.addAppender(rpcLoggerAppender);
    }
}

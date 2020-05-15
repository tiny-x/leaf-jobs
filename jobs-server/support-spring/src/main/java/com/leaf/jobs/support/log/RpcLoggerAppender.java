package com.leaf.jobs.support.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
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
import org.slf4j.LoggerFactory;

import static com.leaf.jobs.constants.JobsConstants.LONGS_PATTERN;

/**
 * @author yefei
 */
@Slf4j
public class RpcLoggerAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static LogsProvider logsProvider;

    public static void setLogsProvider(LogsProvider logsProvider) {
        RpcLoggerAppender.logsProvider = logsProvider;
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        Preconditions.checkArgument(logsProvider !=  null, "logsProvider is null");

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern(LONGS_PATTERN);
        encoder.start();

        String message = new String(encoder.encode(iLoggingEvent));

        logsProvider.transfer(new LogsDTO()
                .setRecordId(Long.valueOf(RpcContext.getAttachment(JobsConstants.RECORD_ID_ATTACH_KEY)))
                .setContent(message)
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

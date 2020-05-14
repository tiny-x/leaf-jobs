package com.leaf.jobs.integration.rpc;

import com.leaf.common.annotation.ServiceProvider;
import com.leaf.jobs.LogsProvider;
import com.leaf.jobs.context.EventBusContext;
import com.leaf.jobs.model.LogsDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yefei
 */
@Slf4j
@ServiceProvider
public class LogsProviderImpl implements LogsProvider {

    @Override
    public void transfer(LogsDTO logsDTO) {
        // TODO 持久化
        log.info("----- recover , recover id:{}, content: {}", logsDTO.getRecordId(), logsDTO.getContent());
        EventBusContext.onMessage(logsDTO.getRecordId(), logsDTO);
    }
}

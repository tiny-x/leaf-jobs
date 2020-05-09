package com.leaf.jobs;

import com.leaf.common.annotation.ServiceProvider;
import com.leaf.jobs.model.LogsDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServiceProvider
public class LogsProviderImpl implements LogsProvider {

    @Override
    public void transfer(LogsDTO logsDTO) {
        // TODO 通过 socket.io 回显到前段
        log.info("----- recover , recover id:{}, content: {}", logsDTO.getRecordId(), logsDTO.getContent());
    }
}

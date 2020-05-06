package com.leaf.jobs;

import com.leaf.common.annotation.ServiceProvider;
import com.leaf.jobs.log.LogsProvider;
import com.leaf.jobs.model.JobVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServiceProvider
public class LogsProviderImpl implements LogsProvider {

    @Override
    public void transfer(JobVo jobVo) {
        // TODO 通过 socket.io 回显到前段
        log.info(jobVo.getMethodName());
    }
}

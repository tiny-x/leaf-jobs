package com.leaf.jobs.impl;

import com.leaf.common.context.RpcContext;
import com.leaf.jobs.DataProcessService;
import com.leaf.jobs.constants.JobsConstants;
import com.leaf.jobs.support.JobsProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@JobsProvider(group = "dp")
public class DataProcessServiceImpl implements DataProcessService {

    @Override
    public void transfer() throws Exception {

        log.info("--- data  process ---: {}", RpcContext.getAttachment(JobsConstants.RECORD_ID_ATTACH_KEY));
        for (int i = 0; i < 2000; i++) {
            Thread.sleep(50);
            log.info("--- data  process ---: {} , {}", RpcContext.getAttachment(JobsConstants.RECORD_ID_ATTACH_KEY), Math.random());
        }
    }
}

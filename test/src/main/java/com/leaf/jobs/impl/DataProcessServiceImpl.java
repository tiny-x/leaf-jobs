package com.leaf.jobs.impl;

import com.leaf.jobs.DataProcessService;
import com.leaf.jobs.spi.JobsProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@JobsProvider(group = "dp")
public class DataProcessServiceImpl implements DataProcessService {

    @Override
    public void transfer() {
        log.info("--- data  process ---");
    }
}

package com.leaf.jobs.impl;

import com.leaf.jobs.ScannerService;
import com.leaf.jobs.auto.config.JobsProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@JobsProvider(group = "scanner")
public class ScannerServiceImpl implements ScannerService {

    @Override
    public String scannerUser() throws Exception {
        log.info("------- scanner user --------");
        Thread.sleep(3000);
        return "success";
    }
}

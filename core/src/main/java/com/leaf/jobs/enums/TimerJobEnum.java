package com.leaf.jobs.enums;

import com.leaf.jobs.job.RpcTimerJob;
import org.quartz.Job;

public enum TimerJobEnum {

    LOCAL(null),
    RPC(RpcTimerJob.class);

    private Class<? extends Job> clazz;

    TimerJobEnum(Class<? extends Job> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Job> getClazz() {
        return clazz;
    }
}

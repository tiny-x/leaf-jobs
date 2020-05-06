package com.leaf.jobs.log;

import com.leaf.common.annotation.ServiceInterface;
import com.leaf.jobs.model.JobVo;

/**
 * 日志通过rpc传输过来
 */
@ServiceInterface(group = "jobs-log")
public interface LogsProvider {

    void transfer(JobVo jobVo);
}

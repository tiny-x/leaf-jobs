package com.leaf.jobs;

import com.leaf.common.annotation.ServiceInterface;
import com.leaf.jobs.model.LogsDTO;

/**
 * 日志通过rpc传输过来
 * @author yefei
 */
@ServiceInterface(group = "jobs-log")
public interface LogsProvider {

    /**
     * 传输日志
     *
     * @param logsDTO
     */
    void transfer(LogsDTO logsDTO);
}

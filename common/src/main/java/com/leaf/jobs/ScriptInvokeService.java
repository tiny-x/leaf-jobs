package com.leaf.jobs;

import com.leaf.jobs.model.Invocation;

/**
 * 脚本执行服务
 *
 * @author yefei
 */
public interface ScriptInvokeService {

    /**
     *
     * @param invocation
     * @return
     */
    Invocation invoke(Invocation invocation);
}

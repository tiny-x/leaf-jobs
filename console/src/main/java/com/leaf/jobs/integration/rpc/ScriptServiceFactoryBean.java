package com.leaf.jobs.integration.rpc;

import com.leaf.jobs.ScriptInvokeService;
import com.leaf.jobs.constants.JobsConstants;
import com.leaf.rpc.DefaultProxyFactory;
import com.leaf.rpc.consumer.Consumer;
import com.leaf.rpc.consumer.InvokeType;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yefei
 */
@Component
public class ScriptServiceFactoryBean implements FactoryBean<ScriptInvokeService> {

    @Autowired
    private Consumer consumer;

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public ScriptInvokeService getObject() throws Exception {
        ScriptInvokeService scriptInvokeService  = DefaultProxyFactory.factory(ScriptInvokeService.class)
                .consumer(consumer)
                .invokeType(InvokeType.ASYNC)
                .group(JobsConstants.SCRIPT_SERVICE_GROUP)
                .newProxy();

        return scriptInvokeService;
    }

    @Override
    public Class<?> getObjectType() {
        return ScriptInvokeService.class;
    }
}

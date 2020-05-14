package com.leaf.jobs.integration.rpc;

import com.leaf.register.api.RegisterType;
import com.leaf.rpc.local.ServiceRegistry;
import com.leaf.rpc.local.ServiceWrapper;
import com.leaf.rpc.provider.DefaultProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yefei
 */
@Component
public class LogsProviderInit implements InitializingBean {

    @Value("${leaf.jobs.registerAddress}")
    private String registerAddress;

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultProvider provider = new DefaultProvider(11222, RegisterType.ZOOKEEPER);
        provider.connectToRegistryServer(registerAddress);
        provider.start();

        LogsProviderImpl logsProvider = new LogsProviderImpl();
        ServiceRegistry serviceRegistry = provider
                .serviceRegistry()
                .provider(logsProvider);

        ServiceWrapper serviceWrapper = serviceRegistry.register();
        provider.publishService(serviceWrapper);
    }
}

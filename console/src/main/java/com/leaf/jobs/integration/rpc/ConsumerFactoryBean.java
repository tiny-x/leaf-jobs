package com.leaf.jobs.integration.rpc;

import com.leaf.common.concurrent.ConcurrentSet;
import com.leaf.common.model.ServiceMeta;
import com.leaf.common.utils.Collections;
import com.leaf.jobs.context.JobsContext;
import com.leaf.register.api.NotifyEvent;
import com.leaf.register.api.RegisterService;
import com.leaf.register.api.RegisterType;
import com.leaf.register.api.model.RegisterMeta;
import com.leaf.register.api.model.SubscribeMeta;
import com.leaf.rpc.consumer.DefaultLeafClient;
import com.leaf.rpc.consumer.LeafClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsumerFactoryBean implements FactoryBean<LeafClient>, InitializingBean, DisposableBean {

    private LeafClient client;

    @Value("${leaf.jobs.systemName}")
    private String application;

    @Value("${leaf.jobs.registerAddress}")
    private String registerAddress;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = new DefaultLeafClient(application, RegisterType.ZOOKEEPER);
        client.connectToRegistryServer(registerAddress);
        RegisterService registerService = client.registerService();
        List<ServiceMeta> serviceMetas = registerService.lookup();
        if (Collections.isNotEmpty(serviceMetas)) {
            for (ServiceMeta serviceMeta : serviceMetas) {
                SubscribeMeta subscribeMeta = new SubscribeMeta();
                subscribeMeta.setServiceMeta(serviceMeta);
                registerService.subscribeRegisterMeta(subscribeMeta, (registerMeta, notifyEvent) -> {
                    ConcurrentSet<RegisterMeta> registerMetas = new ConcurrentSet<>();
                    ConcurrentSet<RegisterMeta> oldRegisterMetas = JobsContext.getGroupMap().putIfAbsent(registerMeta.getServiceMeta().getGroup(), registerMetas);
                    if (oldRegisterMetas != null) {
                        registerMetas = oldRegisterMetas;
                    }
                    if (notifyEvent == NotifyEvent.ADD) {
                        registerMetas.add(registerMeta);
                    } else {
                        registerMetas.remove(registerMeta);
                    }
                });
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        this.client.shutdown();
    }

    @Override
    public LeafClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return LeafClient.class;
    }

}

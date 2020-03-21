package com.leaf.jobs.integration.rpc;

import com.leaf.common.concurrent.ConcurrentSet;
import com.leaf.jobs.context.JobsContext;
import com.leaf.register.api.RegisterType;
import com.leaf.register.api.model.RegisterMeta;
import com.leaf.rpc.consumer.Consumer;
import com.leaf.rpc.consumer.DefaultConsumer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ConsumerFactoryBean implements FactoryBean<Consumer>, InitializingBean {

    private Consumer consumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        Consumer consumer = new DefaultConsumer("jobs", RegisterType.ZOOKEEPER);

        consumer.connectToRegistryServer("zookeeper.dev.xianglin.com:2181");
        consumer.subscribeGroup((registerMeta, notifyEvent) -> {
            ConcurrentSet<RegisterMeta> registerMetas = new ConcurrentSet<>();
            ConcurrentSet<RegisterMeta> oldRegisterMetas = JobsContext.getGroupMap().putIfAbsent(registerMeta.getServiceMeta().getGroup(), registerMetas);
            if (oldRegisterMetas != null) {
                registerMetas = oldRegisterMetas;
            }
            registerMetas.add(registerMeta);
        });
        this.consumer = consumer;
    }

    @Override
    public Consumer getObject() throws Exception {
        return consumer;
    }

    @Override
    public Class<?> getObjectType() {
        return Consumer.class;
    }


}

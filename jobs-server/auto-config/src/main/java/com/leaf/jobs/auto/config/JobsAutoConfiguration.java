package com.leaf.jobs.auto.config;

import com.leaf.jobs.LogsProvider;
import com.leaf.jobs.support.log.RpcLoggerAppender;
import com.leaf.register.api.RegisterType;
import com.leaf.rpc.DefaultProxyFactory;
import com.leaf.rpc.consumer.Consumer;
import com.leaf.rpc.consumer.DefaultConsumer;
import com.leaf.rpc.consumer.InvokeType;
import com.leaf.spring.init.bean.ProviderFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yefei
 */
@Configuration
@ConditionalOnClass({ProviderFactoryBean.class})
@EnableConfigurationProperties({JobsProperties.class})
public class JobsAutoConfiguration {

    @Bean
    public ProviderFactoryBean providerFactoryBean(JobsProperties jobsProperties) {
        ProviderFactoryBean providerFactoryBean = new ProviderFactoryBean();
        providerFactoryBean.setGroup(jobsProperties.getSystemName());
        providerFactoryBean.setPort(jobsProperties.getPort());
        providerFactoryBean.setRegistryServer(jobsProperties.getRegisterAddress());
        providerFactoryBean.setRegisterType(RegisterType.ZOOKEEPER.name());

        return providerFactoryBean;
    }

    @Bean
    public LogsProvider consumerFactory(JobsProperties jobsProperties) {
        Consumer consumer = new DefaultConsumer(jobsProperties.getRegisterAddress(), RegisterType.ZOOKEEPER);
        consumer.connectToRegistryServer(jobsProperties.getRegisterAddress());

        LogsProvider logsProvider  = DefaultProxyFactory.factory(LogsProvider.class)
                .consumer(consumer)
                .timeMillis(3000L)
                .invokeType(InvokeType.ASYNC)
                .newProxy();

        RpcLoggerAppender.setLogsProvider(logsProvider);
        return logsProvider;
    }

}

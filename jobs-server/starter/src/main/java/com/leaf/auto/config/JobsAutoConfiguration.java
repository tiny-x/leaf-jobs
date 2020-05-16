package com.leaf.auto.config;

import com.leaf.jobs.LogsProvider;
import com.leaf.jobs.ScriptInvokeService;
import com.leaf.jobs.server.service.ScriptInvokeServiceImpl;
import com.leaf.jobs.server.service.strategy.GroovyScriptInvokeStrategy;
import com.leaf.jobs.server.service.strategy.ScriptInvokeStrategyContext;
import com.leaf.jobs.server.service.strategy.ShellScriptInvokeStrategy;
import com.leaf.jobs.support.log.RpcLoggerAppender;
import com.leaf.register.api.RegisterType;
import com.leaf.rpc.DefaultProxyFactory;
import com.leaf.rpc.consumer.Consumer;
import com.leaf.rpc.consumer.DefaultConsumer;
import com.leaf.rpc.consumer.InvokeType;
import com.leaf.rpc.consumer.dispatcher.DispatchType;
import com.leaf.rpc.local.ServiceRegistry;
import com.leaf.rpc.local.ServiceWrapper;
import com.leaf.spring.init.bean.ProviderFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    public ScriptInvokeService scriptInvokeBean(ProviderFactoryBean providerFactoryBean, JobsProperties jobsProperties) {
        ScriptInvokeService scriptInvokeService = new ScriptInvokeServiceImpl();
        ServiceRegistry serviceRegistry = providerFactoryBean.getProvider()
                .serviceRegistry()
                .provider(scriptInvokeService)
                .group(jobsProperties.getSystemName())
                .interfaceClass(ScriptInvokeService.class);

        ServiceWrapper serviceWrapper = serviceRegistry.register();
        providerFactoryBean.getProvider().publishService(serviceWrapper);
        return scriptInvokeService;
    }

    @Bean
    public LogsProvider consumerFactory(JobsProperties jobsProperties) {
        Consumer consumer = new DefaultConsumer(jobsProperties.getRegisterAddress(), RegisterType.ZOOKEEPER);
        consumer.connectToRegistryServer(jobsProperties.getRegisterAddress());

        // 广播日志
        LogsProvider logsProvider = DefaultProxyFactory.factory(LogsProvider.class)
                .consumer(consumer)
                .timeMillis(3000L)
                .invokeType(InvokeType.ASYNC)
                .dispatchType(DispatchType.BROADCAST)
                .newProxy();

        RpcLoggerAppender.setLogsProvider(logsProvider);
        return logsProvider;
    }

    @ConditionalOnProperty(prefix = "leaf.jobs", name = "script", havingValue = "true")
    @Bean
    public ScriptInvokeStrategyContext scriptInvokeStrategyContext()  {
        ScriptInvokeStrategyContext scriptInvokeStrategyContext = new ScriptInvokeStrategyContext();
        return scriptInvokeStrategyContext;
    }

    @ConditionalOnBean(ScriptInvokeStrategyContext.class)
    @Bean
    public GroovyScriptInvokeStrategy groovyScriptInvokeStrategy() {
        GroovyScriptInvokeStrategy groovyScriptInvokeStrategy = new GroovyScriptInvokeStrategy();
        return groovyScriptInvokeStrategy;
    }

    @ConditionalOnBean(ScriptInvokeStrategyContext.class)
    @Bean
    public ShellScriptInvokeStrategy shellScriptInvokeStrategy() {
        ShellScriptInvokeStrategy shellScriptInvokeStrategy = new ShellScriptInvokeStrategy();
        return shellScriptInvokeStrategy;
    }
}

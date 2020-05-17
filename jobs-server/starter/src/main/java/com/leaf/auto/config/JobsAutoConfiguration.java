package com.leaf.auto.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.leaf.rpc.provider.DefaultLeafServer;
import com.leaf.rpc.provider.LeafServer;
import com.leaf.rpc.provider.process.RequestProcessFilter;
import com.leaf.rpc.provider.process.RequestWrapper;
import com.leaf.rpc.provider.process.ResponseWrapper;
import com.leaf.spring.init.bean.ProviderFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author yefei
 */
@Slf4j
@Configuration
@ConditionalOnClass({ProviderFactoryBean.class})
@EnableConfigurationProperties({JobsProperties.class})
public class JobsAutoConfiguration {

    @Bean
    public LeafServer providerFactoryBean(JobsProperties jobsProperties) {
        LeafServer provider = new DefaultLeafServer(jobsProperties.getPort(), RegisterType.ZOOKEEPER);
        provider.connectToRegistryServer(jobsProperties.getRegisterAddress());
        provider.start();
        return provider;
    }

    @Bean
    public ScriptInvokeService scriptInvokeBean(LeafServer provider, JobsProperties jobsProperties) {
        ScriptInvokeService scriptInvokeService = new ScriptInvokeServiceImpl();
        ServiceRegistry serviceRegistry = provider
                .serviceRegistry()
                .provider(scriptInvokeService)
                .group(jobsProperties.getSystemName())
                .interfaceClass(ScriptInvokeService.class);
        provider.addRequestProcessFilter(new RequestProcessFilter() {

            ObjectMapper mapper = new ObjectMapper();

            @Override
            public void filter(RequestWrapper requestWrapper, ServiceWrapper serviceWrapper) {
                Object[] args = requestWrapper.getArgs();
                if (serviceWrapper.getServiceProvider() instanceof ScriptInvokeService || args == null) {
                    return;
                }

                Method[] methods = serviceWrapper.getServiceProvider().getClass().getMethods();
                String methodName = requestWrapper.getMethodName();
                for (Method method : methods) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (args.length != parameterTypes.length && method.getName().equals(methodName)) {
                        for (int i = 0; i < args.length; i++) {
                            if (parameterTypes[i] == String.class) {
                                continue;
                            }
                            try {
                                Object o = mapper.readValue((String) (args[i]), parameterTypes[i]);
                                args[i] = o;
                            } catch (IOException e) {
                                //ignore
                            }
                        }
                    }
                }
            }

            @Override
            public void filter(ResponseWrapper responseWrapper) {
                ObjectMapper mapper = new ObjectMapper();
                String s;
                try {
                    s = mapper.writeValueAsString(responseWrapper.getResult());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    s = e.getMessage();
                }
                responseWrapper.setResult(s);
            }
        });
        ServiceWrapper serviceWrapper = serviceRegistry.register();
        provider.publishService(serviceWrapper);
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
    public ScriptInvokeStrategyContext scriptInvokeStrategyContext() {
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

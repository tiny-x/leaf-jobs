package com.leaf.jobs.support.spring;

import ch.qos.logback.classic.LoggerContext;
import com.google.common.base.Strings;
import com.leaf.jobs.support.JobsProvider;
import com.leaf.jobs.support.log.RpcLoggerAppender;
import com.leaf.rpc.local.ServiceRegistry;
import com.leaf.rpc.local.ServiceWrapper;
import com.leaf.rpc.provider.LeafServer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 后置处理器，发布rpc 服务
 * @author yefei
 */
public class JobsAnnotationBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    @Value("${leaf.jobs.systemName}")
    private String systemName;

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        JobsProvider annotation = bean.getClass().getAnnotation(JobsProvider.class);
        if (annotation != null) {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            RpcLoggerAppender.configure(loggerContext, bean.getClass());

            LeafServer server = applicationContext.getBean(LeafServer.class);

            ServiceRegistry serviceRegistry = server
                    .serviceRegistry()
                    .provider(bean)
                    .group(Strings.isNullOrEmpty(annotation.group()) ? systemName : annotation.group())
                    .interfaceClass(bean.getClass().getInterfaces()[0])
                    .weight(annotation.weight())
                    .providerName(annotation.serviceProviderName());

            ServiceWrapper serviceWrapper = serviceRegistry.register();
            server.publishService(serviceWrapper);
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

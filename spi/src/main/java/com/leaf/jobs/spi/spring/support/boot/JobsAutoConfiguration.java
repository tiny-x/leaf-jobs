package com.leaf.jobs.spi.spring.support.boot;

import com.leaf.register.api.RegisterType;
import com.leaf.spring.init.bean.ProviderFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}

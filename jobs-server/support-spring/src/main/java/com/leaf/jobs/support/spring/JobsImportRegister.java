package com.leaf.jobs.support.spring;

import com.leaf.jobs.support.JobsProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @author yefei
 */
public class JobsImportRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(JobsScanner.class.getName()));
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(JobsProvider.class));

        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(annoAttrs.getString("basePackages"));

        for (BeanDefinition candidateComponent : candidateComponents) {
            String beanClassName = candidateComponent.getBeanClassName();
            registry.registerBeanDefinition(beanClassName, candidateComponent);
        }

        GenericBeanDefinition jobsAnnotationBeanPostProcessor = new GenericBeanDefinition();
        jobsAnnotationBeanPostProcessor.setBeanClass(JobsAnnotationBeanPostProcessor.class);
        registry.registerBeanDefinition(JobsAnnotationBeanPostProcessor.class.getSimpleName(), jobsAnnotationBeanPostProcessor);
    }

}

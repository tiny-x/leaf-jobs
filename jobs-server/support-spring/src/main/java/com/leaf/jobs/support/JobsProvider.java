package com.leaf.jobs.support;

import com.leaf.common.annotation.ServiceProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JobsProvider 不需要版本号
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ServiceProvider
public @interface JobsProvider {

    String group() default "";

    String serviceProviderName() default "";

    int weight() default 0;
}

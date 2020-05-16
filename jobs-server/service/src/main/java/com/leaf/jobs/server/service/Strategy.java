package com.leaf.jobs.server.service;

import com.leaf.jobs.enums.TaskType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author yefei
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Strategy {

    TaskType value();
}

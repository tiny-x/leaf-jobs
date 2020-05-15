package com.leaf.jobs.enums;

import java.util.Arrays;

/**
 * 任务类型
 * @author yefei
 */
public enum TaskType {

    SERVICE,
    SHELL,
    GROOVY;

    public static TaskType match(String code) {
        return Arrays.asList(TaskType.values()).stream()
                .filter(taskType -> taskType.name().equals(code))
                .findFirst()
                .orElse(null);
    }
}

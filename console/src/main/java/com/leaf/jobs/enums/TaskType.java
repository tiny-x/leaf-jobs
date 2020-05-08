package com.leaf.jobs.enums;

import java.util.Arrays;

/**
 * 任务类型
 */
public enum TaskType {

    SERVICE,
    SHELL,
    GROOVY;

    private String code;

    public static TaskType match(String code) {
        return Arrays.asList(TaskType.values()).stream()
                .filter(taskType -> taskType.code.equals(code))
                .findFirst()
                .orElse(null);
    }
}

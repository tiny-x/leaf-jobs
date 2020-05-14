package com.leaf.jobs.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author yefei
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invocation {

    Long taskId;

    Long recordId;

    String taskTye;

    String script;

    String result;

    StackTraceElement[] stackTraceElements;
}

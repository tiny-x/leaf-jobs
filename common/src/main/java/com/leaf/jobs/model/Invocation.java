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

    String taskTye;

    String script;

    String result;

    String workPath;

    StackTraceElement[] stackTraceElements;
}

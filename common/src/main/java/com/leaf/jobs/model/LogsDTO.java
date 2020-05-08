package com.leaf.jobs.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(chain = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class LogsDTO {

    Long recordId;

    String content;

    String level;

    long timeStamp;

    String logName;

    String threadName;

    StackTraceElement[] stackTraceElements;
}

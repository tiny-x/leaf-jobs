package com.leaf.jobs.enums;

public enum InvokeResult {

    SCHEDULE_FAIL("调度失败"),
    INVOKING("正在执行中"),
    INVOKE_FAIL("执行失败"),
    INVOKE_SUCCESS("执行成功"),
    INVOKE_TIME_OUT("执行超时");

    private String code;

    InvokeResult(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

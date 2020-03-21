package com.leaf.jobs.model;

import java.util.List;
import java.util.Set;

public class Response<T> {

    private static final int SUCCESS_CODE = 0;

    private static final int ERROR_CODE = 5000;

    private int code = 0;

    private int msg;

    private long count;

    private T data;

    public static Response ofSuccess() {
        Response response = new Response();
        return response;
    }

    public static <T> Response ofSuccess(T t) {
        Response response = new Response();
        response.setData(t);
        if (t instanceof List && t != null)
            response.setCount(((List)t).size());

        if (t instanceof Set && t != null)
            response.setCount(((Set)t).size());

        return response;
    }

    public static <T> Response ofFail(String message) {
        Response response = new Response();
        response.setCode(ERROR_CODE);
        response.setData(message);
        return response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMessage() {
        return msg;
    }

    public void setMessage(int message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}

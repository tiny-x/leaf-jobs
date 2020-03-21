package com.leaf.jobs.web.advice;

import com.leaf.jobs.model.Response;
import com.leaf.jobs.exception.JobsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class ExceptionResolve {

    @ExceptionHandler(Exception.class)
    public Response resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                     Exception ex) {
        log.error(ex.getMessage(), ex);
        return Response.ofFail("系统异常，请检查日志: " + ex.getMessage());
    }

    /**
     * 业务异常
     *
     * @param response
     * @param ex
     */
    @ExceptionHandler(JobsException.class)
    public Response resolveBizException(HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        return Response.ofFail("系统异常，请检查日志: " + ex.getMessage());
    }

}

package com.leaf.jobs.server.service.strategy;

import com.google.common.base.Strings;
import com.leaf.jobs.enums.TaskType;
import com.leaf.jobs.model.Invocation;
import com.leaf.jobs.server.service.Strategy;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yefei
 */
@Slf4j
@Strategy(TaskType.SHELL)
public class ShellScriptInvokeStrategy implements ScriptInvokeStrategy {

    @Override
    public Invocation invoke(Invocation invocation) {
        List<String> strList = new ArrayList<>();
        String[] cmd = {"/bin/sh", "-c", invocation.getScript()};
        try {
            File workPath = null;
            if (!Strings.isNullOrEmpty(invocation.getWorkPath())) {
                workPath = new File(invocation.getWorkPath());
            }
            Process process = Runtime.getRuntime().exec(cmd, null, workPath);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                strList.add(line);
            }
        } catch (Exception e) {
            log.error("shell invoke fail: cmd {}", cmd, e);
        }
        invocation.setResult(strList.toString());
        return invocation;
    }
}

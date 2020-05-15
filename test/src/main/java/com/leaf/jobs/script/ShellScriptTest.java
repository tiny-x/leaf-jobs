package com.leaf.jobs.script;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class ShellScriptTest {

    public static void main(String[] args) {
        List<String> ls = runShell("ls", null, "/");
        System.out.println(ls);
    }

    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * @param shStr
     *            需要执行的shell
     * @return
     */
    public static List<String> runShell(String shStr, String[] envp, String workPath) {
        List<String> strList = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shStr}, null, new File(workPath));
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null){
                strList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }
}

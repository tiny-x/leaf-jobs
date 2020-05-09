package com.leaf.jobs.script;

import groovy.lang.GroovyShell;

public class GroovyScriptTest {

    public static void main(String[] args) {
        GroovyShell shell = new GroovyShell();
        shell.evaluate(getScript());
        //运行完，记得将内部的缓存清理
        shell.getClassLoader().clearCache();
    }

    private static String getScript() {
        String script = "List<String> list = new ArrayList<String>();\n" +
                "list.add(\"a\");\n" +
                "list.add(\"b\");\n" +
                "println list;\n" +
                "\n" +
                "def map = ['x':1,'y':4]\n" +
                "map.put(\"a\", 8);\n" +
                "\n" +
                "println map;\n";
        return script;
    }
}

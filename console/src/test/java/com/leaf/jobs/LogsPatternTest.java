package com.leaf.jobs;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogsPatternTest {

    public final static String ESC_START = "[";
    public final static String ESC_END = "m";
    public final static String BOLD = "1;";

    public final static String BLACK_FG = "30";
    public final static String RED_FG = "31";
    public final static String GREEN_FG = "32";
    public final static String YELLOW_FG = "33";
    public final static String BLUE_FG = "34";
    public final static String MAGENTA_FG = "35";
    public final static String CYAN_FG = "36";
    public final static String WHITE_FG = "37";
    public final static String DEFAULT_FG = "39";

    private final static Map<String, String> trans = new HashMap();

    static {
        trans.put(ESC_START, "");
        trans.put(ESC_START + BLACK_FG + ESC_END, "<font color='black'>content</font>");
        trans.put(ESC_START + RED_FG + ESC_END, "<font color='red'>content</font>");
        trans.put(ESC_START + GREEN_FG + ESC_END, "<font color='green'>content</font>");
        trans.put(ESC_START + YELLOW_FG + ESC_END, "<font color='yellow'>content</font>");
        trans.put(ESC_START + BLUE_FG + ESC_END, "<font color='blue'>content</font>");
        trans.put(ESC_START + MAGENTA_FG + ESC_END, "<font color='magenta'>content</font>");
        trans.put(ESC_START + CYAN_FG + ESC_END, "<font color='cyan'>content</font>");
        trans.put(ESC_START + WHITE_FG + ESC_END, "<font color='white'>content</font>");
        trans.put(ESC_START + DEFAULT_FG + ESC_END, "");
    }

    public static void main(String[] args) {
        String content = "2020-05-15 16:34:53.022, [32mINFO [0;39m, [31m[REQUEST_PROCESS#PROVIDER-7] [0;39m [1;35mcom.leaf.jobs.impl.DataProcessServiceImpl [0;39m - --- data process ---: 710892490734960640 , 0.21918654173655705";
        content = content.replaceAll("0;", "").replaceAll("1;", "");
        Pattern p = Pattern.compile("(\\[)([\\d|;]{2,4})(m)");
        Matcher m = p.matcher(content);
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (; m.find(); i = m.end()) {
            System.out.println("start(): " + m.start());
            System.out.println("end(): " + m.end());
            String color = content.substring(m.start(), m.end());
            String html = trans.get(color);
            int contentEnd = content.indexOf(" ", m.end());
            String subContent;
            if (contentEnd != -1) {
                subContent = content.substring(m.end(), contentEnd + 1);
                if (i == 0) {
                    String prefix = content.substring(i, m.start());
                    stringBuilder.append(prefix + html.replaceFirst("content", subContent));
                } else {
                    stringBuilder.append(html.replaceFirst("content", subContent));
                }
            }
        }
        stringBuilder.append(content.substring(i));

        System.out.println(stringBuilder.toString());
    }
}

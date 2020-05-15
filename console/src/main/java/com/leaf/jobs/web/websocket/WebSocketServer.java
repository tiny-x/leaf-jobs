package com.leaf.jobs.web.websocket;

import com.google.common.eventbus.Subscribe;
import com.leaf.jobs.context.EventBusContext;
import com.leaf.jobs.model.LogsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yefei
 */
@Slf4j
@ServerEndpoint("/logs/{recordId}")
@Component
public class WebSocketServer {

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

    Pattern p = Pattern.compile("(\\[)([\\d|;]{2,4})(m)");

    static {
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

    @OnOpen
    public void onOpen(Session session) {
        String recordId = session.getPathParameters().get("recordId");
        log.info("WebSocket on open recordId: {} ,connect success", recordId);
        EventBusContext.registerListener(Long.valueOf(recordId), new EventBusContext.LogsListener() {
            @Override
            @Subscribe
            public void onMessage(LogsDTO logs) throws IOException {

                // TODO
                String content = logs.getContent().replaceAll("0;", "").replaceAll("1;", "");
                Matcher m = p.matcher(content);
                StringBuilder stringBuilder = new StringBuilder();
                int i = 0;
                for (; m.find(); i = m.end()) {
                    String color = content.substring(m.start(), m.end());
                    String html = trans.get(color);
                    int contentEnd = content.indexOf(27, m.end());
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
                session.getBasicRemote().sendText(stringBuilder.toString());
            }
        });
    }

    @OnClose
    public void onClose(Session session) {
        String recordId = session.getPathParameters().get("recordId");
        log.info("WebSocket on close recordId: {} ,connect success", recordId);
        EventBusContext.unRegisterListener(Long.valueOf(recordId));
    }

    /**
     * @param message
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        //TODO
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage(), error);
    }

}

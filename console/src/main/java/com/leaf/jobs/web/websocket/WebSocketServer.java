package com.leaf.jobs.web.websocket;

import com.google.common.eventbus.Subscribe;
import com.leaf.jobs.context.EventBusContext;
import com.leaf.jobs.model.LogsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author yefei
 */
@Slf4j
@ServerEndpoint("/logs")
@Component
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session) {
        String recordId = session.getPathParameters().get("recordId");
        log.info("WebSocket on open recordId: {} ,connect success", recordId);
        EventBusContext.registerListener(Long.valueOf(recordId), new EventBusContext.LogsListener() {
            @Override
            @Subscribe
            public void onMessage(LogsDTO logs) throws IOException {
                session.getBasicRemote().sendText(logs.getContent());
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

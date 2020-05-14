package com.leaf.jobs.context;

import com.google.common.eventbus.EventBus;
import com.leaf.jobs.model.LogsDTO;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yefei
 */
public class EventBusContext {

    private static Map<Long, EventBus> map = new ConcurrentHashMap();

    public static void unRegisterListener(Long recordId) {
        map.remove(recordId);
    }

    public static void registerListener(Long recordId, LogsListener logsListener) {

        EventBus eventBus = map.get(recordId);
        if (eventBus == null) {
            EventBus newEventBus = new EventBus(String.valueOf(recordId));
            eventBus = map.putIfAbsent(recordId, newEventBus);
            if (eventBus == null) {
                eventBus = newEventBus;
            }
        }
        eventBus.register(logsListener);
    }

    public static void onMessage(Long recordId, LogsDTO logs) {
        EventBus eventBus = map.get(recordId);
        if (eventBus != null) {
            eventBus.post(logs);
        }
    }

    public interface LogsListener {

        /**
         *  触发消息
         *
         * @param logs
         * @throws IOException
         */
        void onMessage(LogsDTO logs) throws IOException;
    }
}

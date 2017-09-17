package com.gzunk.jmsperformance.client;

import org.springframework.jms.core.JmsOperations;
import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;


public class ClientApp {

    @Resource
    JmsOperations jmsOperations;

    @Resource
    Destination destination;

    public void postMessages(String message, final int count) {

        for (int i=0; i < count; i++) {
            int finalI = i;
            jmsOperations.send(
                    destination, session -> session.createTextMessage(String.format("%s - %d", message, finalI)));
        }

    }
}

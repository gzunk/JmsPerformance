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

    public void postMessages() {
        System.out.println("Posting Messages");

        for (int i=0; i<100; i++) {
            int finalI = i;
            jmsOperations.send(destination, session -> session.createTextMessage(String.format("MESSAGE: %d", finalI)));
        }

    }
}
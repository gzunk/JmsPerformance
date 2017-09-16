package com.gzunk.jmsperformance.server;

import javax.jms.*;

public class JmspMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message;
        try {
            String mess = textMessage.getText();
            System.out.printf("Received: %s\n", mess);
        } catch (JMSException e) {
            System.out.println("Error");
        }
    }
}

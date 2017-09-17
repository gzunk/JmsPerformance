package com.gzunk.jmsperformance.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JmspMessageListener implements MessageListener {

    Logger LOG = LoggerFactory.getLogger(JmspMessageListener.class);

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message;
        try {
            String mess = textMessage.getText();
            LOG.info("Received: {}", mess);
        } catch (JMSException e) {
            LOG.error(e.getMessage());
        }
    }
}

package com.gzunk.jmsperformance.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource
    ConnectionFactory connectionFactory;

    @Resource
    Destination destination;

    @Bean
    JmspMessageListener messageListener() {
        return new JmspMessageListener();
    }

    @Bean
    DefaultMessageListenerContainer jmsContainer() {
        DefaultMessageListenerContainer mlp = new DefaultMessageListenerContainer();
        mlp.setConnectionFactory(connectionFactory);
        mlp.setDestination(destination);
        mlp.setMessageListener(messageListener());

        return mlp;
    }
}

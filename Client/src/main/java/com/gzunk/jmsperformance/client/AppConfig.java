package com.gzunk.jmsperformance.client;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@Configuration
@PropertySource("classpath:/application.properties")
public class AppConfig {

    @Value("${jms.broker.url}")
    private String address;

    @Bean
    ClientApp clientApp() {
        return new ClientApp();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(address);
    }

    @Bean
    Destination destination(@Value("${jms.queue.name}") String jmsQueueName) {
        return new ActiveMQQueue(jmsQueueName);
    }

    @Bean
    JmsOperations operations() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    @Scope("prototype")
    MainForm mainForm() {
        return new MainForm();
    }
}

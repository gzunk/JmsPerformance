package com.gzunk.jmsperformance.client;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
        // ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(address);
        CachingConnectionFactory factory = new CachingConnectionFactory(new ActiveMQConnectionFactory(address));
        // PooledConnectionFactory factory = new PooledConnectionFactory(address);
        return factory;
    }

    @Bean
    Destination destination(@Value("${jms.queue.name}") String jmsQueueName) {
        ActiveMQQueue queue = new ActiveMQQueue(jmsQueueName);
        return queue;
    }

    @Bean
    JmsOperations operations() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setExplicitQosEnabled(true);
        jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        return jmsTemplate;
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    @Scope("prototype")
    MainForm mainForm() {
        return new MainForm();
    }
}

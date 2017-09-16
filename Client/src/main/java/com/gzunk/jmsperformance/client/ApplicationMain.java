package com.gzunk.jmsperformance.client;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;

@Configuration
@PropertySource("classpath:/application.properties")
public class ApplicationMain {

    public static final String ADDRESS = "tcp://HOMER:61616";

    public static void main(String[] args) {

        System.out.println("Starting Client");

        // Load application context
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationMain.class);

        // Invoke application
        ClientApp clientApp = (ClientApp) ac.getBean("clientApp");
        clientApp.postMessages();

    }

    @Bean
    ClientApp clientApp() {
        return new ClientApp();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(ADDRESS);
    }

    @Bean
    Destination destination(@Value("${jms.queue.name}") String jmsQueueName) {
        return new ActiveMQQueue(jmsQueueName);
    }

    @Bean
    JmsOperations operations() {
        return new JmsTemplate(connectionFactory());
    }


}

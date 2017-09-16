package com.gzunk.jmsperformance.server;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;

@Configuration
@PropertySource("classpath:/application.properties")
public class ActiveMqConfiguration {

    public static final String ADDRESS = "tcp://HOMER:61616";

    private BrokerService broker;

    @Bean(name="jmsQueueDestination")
    public Destination jmsQueueDestination(@Value("${jms.queue.name}") String jmsQueueName)
            throws JMSException {
        return new ActiveMQQueue(jmsQueueName);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(ADDRESS);
    }

    @Bean
    JmsOperations operations() {
        return new JmsTemplate(connectionFactory());
    }

//    @PostConstruct
//    public void startActiveMQ() throws Exception {
//        broker = new BrokerService();
//        // configure the broker
//        broker.setBrokerName("activemq-broker");
//        broker.setDataDirectory("target");
//        broker.addConnector(ADDRESS);
//        broker.setUseJmx(false);
//        broker.setUseShutdownHook(false);
//        broker.start();
//    }
//
//    @PreDestroy
//    public void stopActiveMQ() throws Exception {
//        broker.stop();
//    }

}

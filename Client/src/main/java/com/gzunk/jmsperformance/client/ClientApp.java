package com.gzunk.jmsperformance.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsOperations;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ClientApp {

    private static final Logger LOG = LoggerFactory.getLogger(ClientApp.class);

    @Resource
    JmsOperations jmsOperations;

    @Resource
    Destination destination;

    @Resource
    ExecutorService executorService;

    CountDownLatch latch;

    public void postMessages(String message, final int count) {

        latch = new CountDownLatch(count);
        long startTime = System.currentTimeMillis();
        LOG.info("Countdown latch is at: {}", latch.getCount());

        for (int i=0; i < count; i++) {
            submitMessage(String.format("%s-%d", message, i));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            LOG.error("Countdown latch interrupted: {}", e.getMessage());
        }
        long endTime = System.currentTimeMillis();

        LOG.info("Sent {} messages in {} ms", count, endTime - startTime);
        LOG.info("Countdown latch is at: {}", latch.getCount());
        LOG.info("Active Threads: {}", ((ThreadPoolExecutor)executorService).getActiveCount());

    }

    public void submitMessage(String message) {
        jmsOperations.send(destination, (session) -> session.createTextMessage(String.format("%s", message)));
        latch.countDown();
    }

    public void submitMessageAsync(String message) {
        executorService.submit(()->{
            jmsOperations.send(destination, (session) -> session.createTextMessage(String.format("%s", message)));
            latch.countDown();
        });

    }
}

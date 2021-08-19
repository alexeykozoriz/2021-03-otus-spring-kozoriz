package ru.otus.homework20210721.consumer.consumer;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.concurrent.CountDownLatch;

/**
 * Демонстрационный потребитель сообщений для Кафки
 */
@Component
@Getter
public class DemoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoConsumer.class);
    private final CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    @KafkaListener(topics = "${demo.topic}", groupId = "${demo.groupId}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(MessageFormat.format("Received: {0}", consumerRecord));
        }
        payload = consumerRecord.value().toString();
        latch.countDown();
    }
}

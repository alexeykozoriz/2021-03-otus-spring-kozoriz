package ru.otus.homework20210721.producer.producer.test;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

/**
 * Тестовый потребитель сообщений для Кафки
 */
@Getter
public class TestingConsumer {

    private final CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    @KafkaListener(topics = "${demo.topic}", groupId = "${demo.groupId}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        payload = consumerRecord.value().toString();
        latch.countDown();
    }
}
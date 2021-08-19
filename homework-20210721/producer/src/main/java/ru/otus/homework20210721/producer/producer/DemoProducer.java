package ru.otus.homework20210721.producer.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Демонстрационный поставщик сообщений для Кафки
 */
@Component
public class DemoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public DemoProducer(KafkaTemplate<String, String> kafkaTemplate,
                        @Value("${demo.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(String payload) {
        kafkaTemplate.send(topic, payload);
    }

}

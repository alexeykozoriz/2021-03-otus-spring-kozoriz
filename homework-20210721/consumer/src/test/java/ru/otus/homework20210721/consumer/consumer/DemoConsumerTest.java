package ru.otus.homework20210721.consumer.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class DemoConsumerTest {

    private static final String TESTING_PAYLOAD = "Test payload";

    @Autowired
    public KafkaTemplate<String, String> template;

    @Autowired
    private DemoConsumer demoConsumer;

    @Value("${demo.topic}")
    private String topic;

    @Test
    void receive() throws InterruptedException {

        template.send(topic, TESTING_PAYLOAD);

        demoConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(demoConsumer.getLatch().getCount()).isZero();
        assertThat(demoConsumer.getPayload()).isEqualTo(TESTING_PAYLOAD);
    }
}
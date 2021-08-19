package ru.otus.homework20210721.producer.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.homework20210721.producer.ProducerApplication;
import ru.otus.homework20210721.producer.producer.test.KafkaTestingConfiguration;
import ru.otus.homework20210721.producer.producer.test.TestingConsumer;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Import(KafkaTestingConfiguration.class)
@SpringBootTest(classes = {ProducerApplication.class})
@ActiveProfiles("test")
@DirtiesContext
class DemoProducerTest {

    private static final String TESTING_PAYLOAD = "Test payload";

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private DemoProducer producer;

    @Autowired
    private TestingConsumer testingConsumer;

    @Test
    void send() throws InterruptedException {

        producer.send(TESTING_PAYLOAD);

        testingConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(testingConsumer.getLatch().getCount()).isZero();
        assertThat(testingConsumer.getPayload()).isEqualTo(TESTING_PAYLOAD);
    }
}
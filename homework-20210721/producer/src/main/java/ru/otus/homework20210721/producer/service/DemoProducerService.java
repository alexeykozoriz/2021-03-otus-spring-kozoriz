package ru.otus.homework20210721.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.homework20210721.producer.producer.DemoProducer;

import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * Сервисный класс для отсылки демонстрационных сообщений в Кафку
 */
@RequiredArgsConstructor
@Service
@Profile("!test")
public class DemoProducerService {

    private final DemoProducer demoProducer;

    /**
     * Периодическая отсылка временных меток
     */
    @Scheduled(fixedRateString = "5000")
    public void generateMessage() {
        demoProducer.send(MessageFormat.format("Timestamp: {0}", LocalDateTime.now()));
    }
}

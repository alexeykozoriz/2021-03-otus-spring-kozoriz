package ru.otus.homework20210407.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Конфигурация ответов
 */
@Component
@ConfigurationProperties(prefix = "application")
@Data
public class AnswersConfig {
    private List<Answer> answersByQuestionNumbers;

    /**
     * Ответ
     */
    @Data
    public static class Answer {
        private String number;
        private List<Integer> options;
    }
}

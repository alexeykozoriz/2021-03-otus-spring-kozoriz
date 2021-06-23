package ru.otus.homework20210414.service;

import ru.otus.homework20210414.domain.Answer;

import java.util.List;

/**
 * Интерфейс сервисного класса для оценки ответов
 */
public interface TestingService {

    /**
     * Пройден ли тест
     *
     * @param answers ответы
     * @return true если да
     */
    boolean isTestingPassed(List<Answer> answers);
}

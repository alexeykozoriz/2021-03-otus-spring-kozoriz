package ru.otus.homework20210407.service;

import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.domain.TestingResult;

import java.util.List;

/**
 * Интерфейс сервисного класса для вывода вопросов
 */
public interface PrintService {
    /**
     * Вывод единичного вопроса
     *
     * @param question вопрос
     */
    void print(Question question);

    /**
     * Вывод списка вопросов
     *
     * @param questions список
     */
    void printAll(List<Question> questions);

    /**
     * Вывод результатов тестирования
     *
     * @param testingResult результат тестирования
     */
    void print(TestingResult testingResult);
}

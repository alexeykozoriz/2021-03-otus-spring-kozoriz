package ru.otus.kozoriz.homework20210324.service;

import ru.otus.kozoriz.homework20210324.domain.Question;

import java.util.List;

/**
 * Интерфейс сервисного класса для вывода вопросов
 */
public interface QuestionsPrintService {
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
}

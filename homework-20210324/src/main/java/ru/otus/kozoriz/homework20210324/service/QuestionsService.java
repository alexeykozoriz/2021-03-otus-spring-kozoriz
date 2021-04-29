package ru.otus.kozoriz.homework20210324.service;

import ru.otus.kozoriz.homework20210324.domain.Question;

import java.util.List;

/**
 * Интерфейс сервисного класса для работы с вопросами
 */
public interface QuestionsService {
    /**
     * Все вопросы
     *
     * @return список
     */
    List<Question> findAllQuestions();
}

package ru.otus.homework20210407.service;

import ru.otus.homework20210407.domain.Question;

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

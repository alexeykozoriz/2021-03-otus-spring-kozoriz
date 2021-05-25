package ru.otus.homework20210407.service;

import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.Question;

import java.util.List;

/**
 * Интерфейс сервиса для тестирования
 */
public interface TestingService {

    /**
     * Имя
     */
    String getFirstName();

    /**
     * Фамилия
     */
    String getLastName();

    /**
     * Ответы на вопросы
     */
    List<Answer> getAnswers(List<Question> questions);
}

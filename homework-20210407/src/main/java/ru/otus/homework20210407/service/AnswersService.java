package ru.otus.homework20210407.service;

import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.Question;

import java.util.List;

/**
 * Интерфейс сервисного класса для получения ответов
 */
public interface AnswersService {

    /**
     * Ответы на вопросы
     *
     * @param questions вопросы
     * @return ответы
     * @throws Exception общее исключение
     */
    List<Answer> getAnswers(List<Question> questions) throws Exception;
}

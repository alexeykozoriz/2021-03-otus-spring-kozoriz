package ru.otus.homework20210414.service;


import ru.otus.homework20210414.domain.Answer;
import ru.otus.homework20210414.domain.Question;

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
     */
    List<Answer> getAnswers(List<Question> questions);
}

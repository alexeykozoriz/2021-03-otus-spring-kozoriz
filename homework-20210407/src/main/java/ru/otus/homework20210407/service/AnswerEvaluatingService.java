package ru.otus.homework20210407.service;

import org.springframework.util.CollectionUtils;
import ru.otus.homework20210407.domain.Answer;

import java.util.List;

/**
 * Интерфейс сервиса для оценки ответов на вопросы
 */
public interface AnswerEvaluatingService {

    /**
     * Правильность ответа
     *
     * @param answer ответ
     * @return true если правильный
     */
    boolean isRightAnswer(Answer answer);

    /**
     * Правильность ответов
     *
     * @param answers список ответов
     * @return true если все правильные
     */
    default boolean isAllAnswersRight(List<Answer> answers) {
        if (CollectionUtils.isEmpty(answers)) {
            return false;
        }
        return answers.stream().allMatch(this::isRightAnswer);
    }
}

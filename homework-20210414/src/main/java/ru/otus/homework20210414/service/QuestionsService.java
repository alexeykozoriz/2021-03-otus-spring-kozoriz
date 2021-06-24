package ru.otus.homework20210414.service;

import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.homework20210414.domain.Question;
import ru.otus.homework20210414.error.QuestionsReadingError;

import java.text.MessageFormat;
import java.util.List;

/**
 * Интерфейс сервисного класса для работы с вопросами
 */
public interface QuestionsService {
    /**
     * Все вопросы
     *
     * @return список
     * @throws QuestionsReadingError исключение обработки CSV
     */
    @NonNull
    List<Question> findAllQuestions() throws QuestionsReadingError;

    /**
     * Валидация вопросов
     *
     * @param questions список вопросов
     */
    default void assertQuestionsIsValid(List<Question> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            return;
        }
        questions.forEach(this::assertQuestionIsValid);
    }

    /**
     * Валидация вопроса
     *
     * @param question вопрос
     */
    default void assertQuestionIsValid(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Empty question");
        }
        if (!StringUtils.hasText(question.getText())) {
            throw new IllegalArgumentException("Empty question text");
        }
        if (!StringUtils.hasText(question.getNumber())) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Question {0} has empty number", question.getText()));
        }
    }
}

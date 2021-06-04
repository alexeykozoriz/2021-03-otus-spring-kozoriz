package ru.otus.homework20210407.dao;

import org.springframework.lang.NonNull;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.error.QuestionsReadingError;

import java.util.List;

/**
 * Интерфейс хранилища вопросов
 */
public interface QuestionDao {

    /**
     * Все вопросы
     *
     * @return список
     */
    @NonNull
    List<Question> findAll() throws QuestionsReadingError;
}

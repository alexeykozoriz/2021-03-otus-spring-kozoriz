package ru.otus.homework20210414.dao;

import org.springframework.lang.NonNull;
import ru.otus.homework20210414.domain.Question;
import ru.otus.homework20210414.error.QuestionsReadingError;

import java.util.List;

/**
 * Интерфейс хранилища вопросов
 */
public interface QuestionDao {

    /**
     * Все вопросы
     *
     * @param locale локаль
     * @return список
     */
    @NonNull
    List<Question> findAll(String locale) throws QuestionsReadingError;
}

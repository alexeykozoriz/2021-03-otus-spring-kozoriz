package ru.otus.homework20210407.service;

import org.springframework.lang.NonNull;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.error.CsvReadError;

import java.util.List;

/**
 * Интерфейс сервисного класса для работы с вопросами
 */
public interface QuestionsService {
    /**
     * Все вопросы
     *
     * @return список
     * @throws CsvReadError исключение обработки CSV
     */
    @NonNull
    List<Question> findAllQuestions() throws CsvReadError;
}

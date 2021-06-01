package ru.otus.homework20210407.dao;

import com.opencsv.exceptions.CsvException;
import ru.otus.homework20210407.domain.Question;

import java.io.IOException;
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
    List<Question> findAll() throws IOException, CsvException;
}

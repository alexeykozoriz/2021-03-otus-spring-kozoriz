package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210407.dao.QuestionDao;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.error.CsvReadError;

import java.util.List;

/**
 * Реализация для работы с вопросами в формате CSV
 */
@RequiredArgsConstructor
@Service
public class QuestionsServiceCsvImpl implements QuestionsService {

    private final QuestionDao questionDao;

    @Override
    public List<Question> findAllQuestions() throws CsvReadError {
        return questionDao.findAll();
    }

}

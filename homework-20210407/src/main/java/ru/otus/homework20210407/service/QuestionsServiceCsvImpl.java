package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.otus.homework20210407.dao.QuestionDao;
import ru.otus.homework20210407.domain.Question;

import java.util.List;

/**
 * Реализация для работы с вопросами в формате CSV
 */
@RequiredArgsConstructor
@Service
public class QuestionsServiceCsvImpl implements QuestionsService {

    private final QuestionDao questionDao;

    @SneakyThrows
    @Override
    public List<Question> findAllQuestions() {
        return questionDao.findAll();
    }

}

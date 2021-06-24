package ru.otus.homework20210414.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210414.dao.QuestionDao;
import ru.otus.homework20210414.domain.Question;
import ru.otus.homework20210414.error.QuestionsReadingError;

import java.util.List;

/**
 * Реализация для работы с вопросами в формате CSV
 */
@RequiredArgsConstructor
@Service
public class QuestionsServiceCsvImpl implements QuestionsService {

    private final QuestionDao questionDao;
    private final LocalizationService localizationService;

    @Override
    public List<Question> findAllQuestions() throws QuestionsReadingError {
        return questionDao.findAll(localizationService.getLocale());
    }

}

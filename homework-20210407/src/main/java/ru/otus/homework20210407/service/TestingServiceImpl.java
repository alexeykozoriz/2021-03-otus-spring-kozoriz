package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.AnswerByOption;
import ru.otus.homework20210407.domain.AnswerByText;

import java.util.List;

/**
 * Реализация по-умолчанию.
 * Ответы на вопросы без опций всегда правильные.
 * Ответы на вопросы с опциями правильные, если совпадают с ответами.
 */
@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {

    @Override
    public boolean isTestingPassed(List<Answer> answers) {
        return !CollectionUtils.isEmpty(answers)
                && answers.stream()
                .allMatch(p -> p instanceof AnswerByText
                        || CollectionUtils.isEmpty(p.getQuestion().getRightOptions())
                        || p.getQuestion().getRightOptions().contains(((AnswerByOption) p).getSelectedOption()));
    }
}

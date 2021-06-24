package ru.otus.homework20210414.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210414.domain.Answer;
import ru.otus.homework20210414.domain.AnswerByOption;
import ru.otus.homework20210414.domain.AnswerByText;

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
        if (CollectionUtils.isEmpty(answers)) {
            return false;
        }
        return answers.stream()
                .allMatch(p -> isAnswerByText(p) || isQuestionWithoutRightOptions(p) || isRightOptionSelected(p));
    }

    /**
     * Выбрана правильная опция
     *
     * @param p ответ
     * @return true если да
     */
    private boolean isRightOptionSelected(Answer p) {
        return p.getQuestion().getRightOptions().contains(((AnswerByOption) p).getSelectedOption());
    }

    /**
     * Вопрос с опциями не содержит правильных вариантов ответа
     *
     * @param p ответ
     * @return true если да
     */
    private boolean isQuestionWithoutRightOptions(Answer p) {
        return CollectionUtils.isEmpty(p.getQuestion().getRightOptions());
    }

    /**
     * Вопрос с ответом в свободной форме
     *
     * @param p ответ
     * @return true если да
     */
    private boolean isAnswerByText(Answer p) {
        return p instanceof AnswerByText;
    }
}

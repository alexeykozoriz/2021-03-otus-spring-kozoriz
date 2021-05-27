package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210407.config.AnswersConfig;
import ru.otus.homework20210407.domain.Answer;

import java.util.Objects;

/**
 * Реализация для оценки по YML-конфигу
 */
@Service
@RequiredArgsConstructor
public class AnswerEvaluatingServiceYmlImpl implements AnswerEvaluatingService {

    private final AnswersConfig answersConfig;

    @Override
    public boolean isRightAnswer(Answer answer) {
        return Objects.requireNonNull(answersConfig.getAnswersByQuestionNumbers(), "Answers not configured").stream()
                .filter(p -> p.getNumber().equals(answer.getQuestion().getNumber()))
                .findFirst()
                .map(p -> p.getOptions().contains(answer.getSelectedOption()))
                .orElse(true);
    }
}

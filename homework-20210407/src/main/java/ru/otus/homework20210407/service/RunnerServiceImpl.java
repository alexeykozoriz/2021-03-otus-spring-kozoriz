package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210407.domain.AnswerByText;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Реализация для проведения тестирования через консоль
 */
@Service
@RequiredArgsConstructor
public class RunnerServiceImpl implements RunnerService {

    private final QuestionsService questionsService;
    private final AnswersService answersService;
    private final TestingService testingService;
    private final InteractionService interactionService;
    private final ResourceBundle resourceBundle;

    @Override
    public void runTesting() throws Exception {
        final var allAnswers = answersService.getAnswers(
                questionsService.findAllQuestions());
        if (CollectionUtils.isEmpty(allAnswers)) {
            return;
        }
        interactionService.outputString(
                MessageFormat.format("{0} : {1}",
                        allAnswers.stream()
                                .filter(AnswerByText.class::isInstance)
                                .map(p -> ((AnswerByText) p).getText())
                                .collect(Collectors.joining(" ")),
                        resourceBundle.getString(
                                testingService.isTestingPassed(allAnswers) ? "success" : "fail")));
    }
}

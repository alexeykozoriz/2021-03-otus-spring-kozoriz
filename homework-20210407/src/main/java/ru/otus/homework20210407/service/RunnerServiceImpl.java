package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210407.domain.AnswerByText;

import java.util.Locale;
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
    private final MessageSource messageSource;

    @Override
    public void runTesting() throws Exception {
        final var allQuestions = questionsService.findAllQuestions();
        questionsService.assertQuestionsIsValid(allQuestions);
        final var allAnswers = answersService.getAnswers(allQuestions);
        if (CollectionUtils.isEmpty(allAnswers)) {
            return;
        }
        final var resultMessage = testingService.isTestingPassed(allAnswers)
                ? "success"
                : "fail";
        final var textInfo = allAnswers.stream()
                .filter(AnswerByText.class::isInstance)
                .map(p -> ((AnswerByText) p).getText())
                .collect(Collectors.joining(" "));
        interactionService.outputString(
                messageSource.getMessage(resultMessage,
                        new String[]{textInfo},
                        Locale.forLanguageTag("ru-RU")));
    }
}

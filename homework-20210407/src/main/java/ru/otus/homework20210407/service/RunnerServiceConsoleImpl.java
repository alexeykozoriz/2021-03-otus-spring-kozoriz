package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210407.domain.AnswerByText;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.error.CsvReadError;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация для проведения тестирования через консоль
 */
@Service
@RequiredArgsConstructor
public class RunnerServiceConsoleImpl implements RunnerService {

    private final QuestionsService questionsService;
    private final AnswersService answersService;
    private final TestingService testingService;

    @Override
    public void runTesting() throws Exception {
        final List<Question> allQuestions;
        try {
            allQuestions = questionsService.findAllQuestions();
        } catch (CsvReadError csvReadError) {
            throw csvReadError.getInnerException();
        }
        final var answers = answersService.getAnswers(allQuestions);
        String sb = MessageFormat.format("{0} : {1}",
                answers.stream()
                        .filter(AnswerByText.class::isInstance)
                        .map(p -> ((AnswerByText) p).getText())
                        .collect(Collectors.joining(" ")),
                testingService.isTestingPassed(answers) ? "SUCCESS" : "FAIL");
        System.out.println(sb);
    }
}

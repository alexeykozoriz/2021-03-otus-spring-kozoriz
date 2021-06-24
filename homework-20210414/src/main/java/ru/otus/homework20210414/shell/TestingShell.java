package ru.otus.homework20210414.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210414.domain.AnswerByText;
import ru.otus.homework20210414.domain.Question;
import ru.otus.homework20210414.error.QuestionsReadingError;
import ru.otus.homework20210414.service.*;

import java.text.MessageFormat;
import java.util.stream.Collectors;

/**
 * Компонент для тестирования
 */
@ShellComponent
@RequiredArgsConstructor
public class TestingShell {

    private final QuestionsService questionsService;
    private final InteractionService interactionService;
    private final AnswersService answersService;
    private final TestingService testingService;
    private final LocalizationService localizationService;

    /**
     * Запуск тестирования
     *
     * @throws QuestionsReadingError ошибка чтения вопросов
     */
    @ShellMethod(key = {"run-testing", "rt"}, value = "Run testing by any questions")
    public void runTesting() throws QuestionsReadingError {
        final var allQuestions = questionsService.findAllQuestions();
        questionsService.assertQuestionsIsValid(allQuestions);
        final var allAnswers = answersService.getAnswers(allQuestions);
        if (CollectionUtils.isEmpty(allAnswers)) {
            return;
        }
        final var resultMessageKey = testingService.isTestingPassed(allAnswers)
                ? "success"
                : "fail";
        final var textInfo = allAnswers.stream()
                .filter(AnswerByText.class::isInstance)
                .map(p -> ((AnswerByText) p).getText())
                .collect(Collectors.joining(" "));
        final var result = localizationService.getLocalizedString(resultMessageKey, textInfo);
        interactionService.outputString(result);
    }

    /**
     * Вывод всех вопросов с опциями
     *
     * @throws QuestionsReadingError ошибка чтения вопросов
     */
    @ShellMethod(key = {"print-questions", "pq"}, value = "Print all questions")
    public void printAllQuestions() throws QuestionsReadingError {
        final var allQuestions = questionsService.findAllQuestions();
        for (Question question : allQuestions) {
            final var options = String.join(", ", question.getOptions());
            final var rightOptions = question.getRightOptions().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            final var questionInfo = MessageFormat.format("{0}) {1} // {2} ({3})",
                    question.getNumber(), question.getText(), options, rightOptions);
            interactionService.outputString(questionInfo);
        }
    }
}

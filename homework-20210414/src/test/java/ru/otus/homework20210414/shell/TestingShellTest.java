package ru.otus.homework20210414.shell;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework20210414.domain.AnswerByOption;
import ru.otus.homework20210414.domain.AnswerByText;
import ru.otus.homework20210414.domain.Question;
import ru.otus.homework20210414.error.QuestionsReadingError;
import ru.otus.homework20210414.service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class TestingShellTest {
    @Mock
    private QuestionsService questionsService;
    @Mock
    private AnswersService answersService;
    @Mock
    private TestingService testingService;
    @Mock
    private InteractionService interactionService;
    @Mock
    private LocalizationService localizationService;
    @InjectMocks
    private TestingShell testingShell;


    /**
     * Выполнение алгоритма тестирования
     *
     * @throws QuestionsReadingError ошибка чтения вопросов
     */
    @Test
    void runTesting() throws QuestionsReadingError {
        final var questions = List.of(
                new Question("1", "Enter text", null, null),
                new Question("1", "2x2=?", List.of("2", "22", "4"), Collections.singletonList(3)));
        when(questionsService.findAllQuestions()).thenReturn(questions);
        final var answers = List.of(
                new AnswerByText(questions.get(0), "Text"),
                new AnswerByOption(questions.get(1), 3));
        when(answersService.getAnswers(questions)).thenReturn(answers);
        when(testingService.isTestingPassed(answers)).thenReturn(true);
        when(localizationService.getLocalizedString(any(), any())).thenReturn("Text : SUCCESS");
        testingShell.runTesting();
        InOrder inOrder = inOrder(questionsService, answersService, testingService, interactionService, localizationService);
        inOrder.verify(questionsService, times(1)).findAllQuestions();
        inOrder.verify(answersService, times(1)).getAnswers(questions);
        inOrder.verify(testingService, times(1)).isTestingPassed(answers);
        inOrder.verify(localizationService, times(1)).getLocalizedString(any(), any());
        inOrder.verify(interactionService, times(1)).outputString("Text : SUCCESS");
    }

    /**
     * Null-проверка для ответов
     */
    @Test
    void runTestingWithEmptyAnswers() {
        when(answersService.getAnswers(any())).thenReturn(null);
        assertDoesNotThrow(testingShell::runTesting);
    }

    /**
     * Обработка пустого списка с вопросами
     */
    @Test
    void printAllQuestionsWithEmptyQuestions() throws QuestionsReadingError {
        when(questionsService.findAllQuestions()).thenReturn(new ArrayList<>());
        assertDoesNotThrow(testingShell::printAllQuestions);
    }
}
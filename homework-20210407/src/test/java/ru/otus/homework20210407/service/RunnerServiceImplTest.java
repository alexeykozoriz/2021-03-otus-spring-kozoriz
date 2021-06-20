package ru.otus.homework20210407.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework20210407.domain.AnswerByOption;
import ru.otus.homework20210407.domain.AnswerByText;
import ru.otus.homework20210407.domain.Question;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Класс RunnerServiceImpl")
@ExtendWith(MockitoExtension.class)
class RunnerServiceImplTest {

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
    private RunnerServiceImpl runnerService;

    /**
     * Выполнение алгоритма тестирования
     *
     * @throws Exception общее исключение
     */
    @Test
    void runTesting() throws Exception {
        final var questions = List.of(
                new Question("1", "Enter fullname", null, null),
                new Question("1", "2x2=?", List.of("2", "22", "4"), Collections.singletonList(3)));
        when(questionsService.findAllQuestions()).thenReturn(questions);
        final var answers = List.of(
                new AnswerByText(questions.get(0), "Text"),
                new AnswerByOption(questions.get(1), 3));
        when(answersService.getAnswers(questions)).thenReturn(answers);
        when(testingService.isTestingPassed(answers)).thenReturn(true);
        when(localizationService.getLocalizedString(any(), any())).thenReturn("Text : SUCCESS");
        runnerService.runTesting();
        InOrder inOrder = inOrder(questionsService, answersService, testingService, interactionService, localizationService);
        inOrder.verify(questionsService, times(1)).findAllQuestions();
        inOrder.verify(questionsService, times(1)).assertQuestionsIsValid(questions);
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
        assertDoesNotThrow(runnerService::runTesting);
    }
}
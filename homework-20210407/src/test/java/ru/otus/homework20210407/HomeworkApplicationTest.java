package ru.otus.homework20210407;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.domain.TestingResult;
import ru.otus.homework20210407.service.AnswerEvaluatingService;
import ru.otus.homework20210407.service.PrintService;
import ru.otus.homework20210407.service.QuestionsService;
import ru.otus.homework20210407.service.TestingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Класс HomeworkApplication")
@ExtendWith(MockitoExtension.class)
class HomeworkApplicationTest {
    private HomeworkApplication homeworkApplication;

    public static final String IVAN = "Ivan";
    public static final String IVANOV = "Ivanov";
    private static final String[] EMPTY_ARRAY = new String[0];
    private static final List<Question> questions;
    private static final Map<Integer, Boolean> testResultCases = new HashMap<>();

    static {
        questions = List.of(new Question("A", "2x2 = ?", List.of("2", "22", "4")));
        testResultCases.put(0, false);
        testResultCases.put(1, false);
        testResultCases.put(2, true);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2 })
    void runTest(int selectedOption,
                 @Mock AnswerEvaluatingService answerEvaluatingService,
                 @Mock TestingService testingService,
                 @Mock QuestionsService questionsService,
                 @Mock PrintService printService) {
        when(questionsService.findAllQuestions())
                .thenReturn(questions);
        when(testingService.getFirstName())
                .thenReturn(IVAN);
        when(testingService.getLastName())
                .thenReturn(IVANOV);
        final var answers = List.of(new Answer(questions.get(0), selectedOption));
        when(testingService.getAnswers(questions))
                .thenReturn(answers);
        when(answerEvaluatingService.isAllAnswersRight(answers)).thenReturn(selectedOption == 2);
        homeworkApplication = new HomeworkApplication(answerEvaluatingService, testingService, questionsService, printService);

        assertDoesNotThrow(() -> homeworkApplication.run(EMPTY_ARRAY));

        ArgumentCaptor<TestingResult> testingResult = ArgumentCaptor.forClass(TestingResult.class);
        verify(printService, times(1)).print(testingResult.capture());
        assertEquals(IVAN, testingResult.getValue().getFirstName());
        assertEquals(IVANOV, testingResult.getValue().getLastName());
        assertEquals(testResultCases.get(selectedOption), testingResult.getValue().isPassed());
    }
}
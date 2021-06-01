package ru.otus.homework20210407.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.AnswerByOption;
import ru.otus.homework20210407.domain.Question;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс RunnerServiceConsoleImpl")
@ExtendWith(MockitoExtension.class)
class TestingServiceImplTest {

    public static final Question QUESTION_1 = new Question("1", "2x2=?", Arrays.asList("1", "2", "3", "4"), Collections.singletonList(4));
    private static final String HAPPY = "Happy";
    private static final String WRONG = "Wrong";
    private static final Map<String, List<Answer>> answersByCase = new HashMap<>();
    private static final Map<String, Boolean> resultByCase = new HashMap<>();

    static {
        answersByCase.put(HAPPY, Collections.singletonList(new AnswerByOption(QUESTION_1, 4)));
        resultByCase.put(HAPPY, true);
        answersByCase.put(WRONG, Collections.singletonList(new AnswerByOption(QUESTION_1, 1)));
        resultByCase.put(WRONG, false);
    }

    private final TestingService testingService = new TestingServiceImpl();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @ValueSource(strings = {HAPPY, WRONG})
    void isTestingPassedTest(String caseName) {
        assertEquals(resultByCase.get(caseName),
                testingService.isTestingPassed(answersByCase.get(caseName)));
    }
}
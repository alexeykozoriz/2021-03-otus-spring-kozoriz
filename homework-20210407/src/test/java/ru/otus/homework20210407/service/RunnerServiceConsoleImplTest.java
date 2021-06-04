package ru.otus.homework20210407.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework20210407.error.QuestionsReadingError;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Класс RunnerServiceConsoleImpl")
@ExtendWith(MockitoExtension.class)
class RunnerServiceConsoleImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void runTestingWithEmptyQuestions(@Mock QuestionsService questionsService,
                                      @Mock AnswersService answersService,
                                      @Mock TestingService testingService,
                                      @Mock InteractionService interactionService) throws QuestionsReadingError {
        var runnerService = new RunnerServiceConsoleImpl(questionsService, answersService, testingService, interactionService);
        when(questionsService.findAllQuestions()).thenReturn(null);
        assertDoesNotThrow(runnerService::runTesting);
    }

    @Test
    void runTestingWithEmptyAnswers(@Mock QuestionsService questionsService,
                                    @Mock AnswersService answersService,
                                    @Mock TestingService testingService,
                                    @Mock InteractionService interactionService) throws QuestionsReadingError {
        var runnerService = new RunnerServiceConsoleImpl(questionsService, answersService, testingService, interactionService);
        when(answersService.getAnswers(any())).thenReturn(null);
        assertDoesNotThrow(runnerService::runTesting);
    }
}
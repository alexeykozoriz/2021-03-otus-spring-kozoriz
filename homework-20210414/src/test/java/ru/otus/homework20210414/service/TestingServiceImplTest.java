package ru.otus.homework20210414.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework20210414.domain.AnswerByOption;
import ru.otus.homework20210414.domain.Question;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс TestingServiceImpl")
class TestingServiceImplTest {

    private final TestingService testingService = new TestingServiceImpl();

    /**
     * Совпадение с ответами для вопросов с опциями
     */
    @Test
    void isTestingPassed() {
        var question = new Question("1", "2x2=?", Arrays.asList("1", "2", "3", "4"), Collections.singletonList(4));
        assertTrue(testingService.isTestingPassed(
                Collections.singletonList(
                        new AnswerByOption(question, 4))));
        assertFalse(testingService.isTestingPassed(
                Collections.singletonList(
                        new AnswerByOption(question, 1))));
    }

    /**
     * Null-проверка входного параметра
     */
    @Test
    void isTestingPassedWithEmptyAnswers() {
        assertDoesNotThrow(() -> testingService.isTestingPassed(null));
    }
}
package ru.otus.homework20210414.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework20210414.domain.Question;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Класс AnswersServiceImpl")
@ExtendWith(MockitoExtension.class)
class AnswersServiceImplTest {

    @Mock
    private InteractionService interactionService;
    @Mock
    private LocalizationService localizationService;
    @InjectMocks
    private AnswersServiceImpl answersService;

    /**
     * Вызов interactionService.readString() для вопроса без опций
     */
    @Test
    void getAnswersByText() {
        answersService.getAnswers(
                Collections.singletonList(
                        new Question("1", "Test text", null, null)));
        verify(interactionService, times(1)).readString(any());
    }

    /**
     * Вызов interactionService.readIntByInterval() для вопроса с опциями
     */
    @Test
    void getAnswersByOption() {
        answersService.getAnswers(
                Collections.singletonList(
                        new Question("1", "2x2=?", List.of("2", "22", "4"), Collections.singletonList(3))));
        verify(interactionService, times(1)).readIntByInterval(any(), eq(3));
    }
}
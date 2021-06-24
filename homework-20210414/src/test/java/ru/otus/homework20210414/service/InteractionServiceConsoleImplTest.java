package ru.otus.homework20210414.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Класс InteractionServiceConsoleImpl")
@ExtendWith(MockitoExtension.class)
class InteractionServiceConsoleImplTest {

    private static final String ANY_PROMPT = "Prompt";
    private static final String TEST_TEXT = "Test text";
    private static final String TEST_INPUT_STRING = "Test in";

    @Mock
    private LocalizationService localizationService;

    /**
     * Чтение заданной строки из System.in
     */
    @Test
    void readString() {
        var interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream(TEST_INPUT_STRING.getBytes()),
                new PrintStream(new ByteArrayOutputStream()),
                localizationService);
        assertEquals(TEST_INPUT_STRING,
                interactionService.readString(ANY_PROMPT));
    }

    /**
     * Чтение целых чисел в заданном диапазоне из System.in
     */
    @Test
    void readIntByInterval() throws IOException {
        when(localizationService.getLocalizedString(anyString()))
                .thenReturn("STRING MOCK");

        // число меньше интервала
        final var interval = 4;
        var interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream("0\n1".getBytes()),
                new PrintStream(new ByteArrayOutputStream()),
                localizationService);
        assertEquals(1,
                interactionService.readIntByInterval(ANY_PROMPT, interval));

        // число из интервала
        interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream("3".getBytes()),
                new PrintStream(new ByteArrayOutputStream()),
                localizationService);
        assertEquals(3,
                interactionService.readIntByInterval(ANY_PROMPT, interval));

        // число больше интервала
        interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream("42\n4".getBytes()),
                new PrintStream(new ByteArrayOutputStream()),
                localizationService);
        assertEquals(4,
                interactionService.readIntByInterval(ANY_PROMPT, interval));
    }

    /**
     * Запись заданной строки в System.out
     */
    @Test
    void outputString() {
        final var out = new ByteArrayOutputStream();
        var interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream("".getBytes()),
                new PrintStream(out),
                localizationService);
        interactionService.outputString(TEST_TEXT);
        final var text = out.toString();
        assertTrue(StringUtils.isNotBlank(text));
        assertEquals(TEST_TEXT, text.trim());
    }
}
package ru.otus.homework20210421.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис взаимодействия с пользователем через консоль")
class InteractionServiceConsoleImplTest {

    private static final String ANY_PROMPT = "Prompt";
    private static final String TEST_TEXT = "Test text";
    private static final String TEST_INPUT_STRING = "Test in";

    @Test
    void readString() {
        var interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream(TEST_INPUT_STRING.getBytes()),
                new PrintStream(new ByteArrayOutputStream()));
        assertEquals(TEST_INPUT_STRING,
                interactionService.readString(ANY_PROMPT));
    }

    @Test
    void readNumberByInterval() {

        // число меньше интервала
        final var minimum = 1;
        final var maximum = 4;
        var interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream("0\n1".getBytes()),
                new PrintStream(new ByteArrayOutputStream()));
        assertEquals(1,
                interactionService.readNumberByInterval(ANY_PROMPT, minimum, maximum));

        // число из интервала
        interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream("3".getBytes()),
                new PrintStream(new ByteArrayOutputStream()));
        assertEquals(3,
                interactionService.readNumberByInterval(ANY_PROMPT, minimum, maximum));

        // число больше интервала
        interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream("42\n4".getBytes()),
                new PrintStream(new ByteArrayOutputStream()));
        assertEquals(4,
                interactionService.readNumberByInterval(ANY_PROMPT, minimum, maximum));
    }

    @Test
    void outputString() {
        final var out = new ByteArrayOutputStream();
        var interactionService = new InteractionServiceConsoleImpl(
                new ByteArrayInputStream("".getBytes()),
                new PrintStream(out));
        interactionService.outputString(TEST_TEXT);
        final var text = out.toString();
        assertTrue(StringUtils.isNotBlank(text));
        assertEquals(TEST_TEXT, text.trim());
    }
}
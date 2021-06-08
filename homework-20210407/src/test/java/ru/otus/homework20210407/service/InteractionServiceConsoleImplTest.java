package ru.otus.homework20210407.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс InteractionServiceConsoleImpl")
class InteractionServiceConsoleImplTest {

    private static final String ANY_PROMPT = "Prompt";
    private static final String TEST_TEXT = "Test text";
    private static final String TEST_INPUT_STRING = "Test in";
    private final InteractionService interactionService = new InteractionServiceConsoleImpl();
    private InputStream originalIn;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Чтение заданной строки из System.in
     */
    @Test
    void readString() {
        System.setIn(
                new ByteArrayInputStream(TEST_INPUT_STRING.getBytes()));
        assertEquals(TEST_INPUT_STRING,
                interactionService.readString(ANY_PROMPT));
    }

    /**
     * Чтение целых чисел в заданном диапазоне из System.in
     */
    @Test
    void readIntByInterval() throws IOException {
        final var interval = 4;
        System.setIn(new ByteArrayInputStream("0\n1".getBytes()));
        assertEquals(1,
                interactionService.readIntByInterval(ANY_PROMPT, interval));
        System.setIn(new ByteArrayInputStream("3".getBytes()));
        assertEquals(3,
                interactionService.readIntByInterval(ANY_PROMPT, interval));
        System.setIn(new ByteArrayInputStream("42\n4".getBytes()));
        assertEquals(interval,
                interactionService.readIntByInterval(ANY_PROMPT, interval));
    }

    /**
     * Запись заданной строки в System.out
     */
    @Test
    void outputString() {
        final var systemOut = new ByteArrayOutputStream();
        System.setOut(
                new PrintStream(systemOut));
        interactionService.outputString(TEST_TEXT);
        final var out = systemOut.toString();
        assertTrue(StringUtils.isNotBlank(out));
        assertEquals(TEST_TEXT, out.trim());
    }
}
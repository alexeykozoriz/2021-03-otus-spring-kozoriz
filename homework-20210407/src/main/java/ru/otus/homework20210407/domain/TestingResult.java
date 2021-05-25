package ru.otus.homework20210407.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Результат тестирования
 */
@Data
@Builder
public class TestingResult {
    private final String firstName;
    private final String lastName;
    private final boolean isPassed;
}

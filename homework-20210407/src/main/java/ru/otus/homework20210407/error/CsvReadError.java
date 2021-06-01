package ru.otus.homework20210407.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Ошибка чтения CSV-файла
 */
@RequiredArgsConstructor
@Getter
public class CsvReadError extends Throwable {
    private final Exception innerException;
}

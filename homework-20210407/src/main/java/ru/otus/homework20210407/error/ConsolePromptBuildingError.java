package ru.otus.homework20210407.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

/**
 * Ошибка сборки подсказки для вывода в консоль
 */
@RequiredArgsConstructor
@Getter
public class ConsolePromptBuildingError extends Throwable {
    private final Exception innerException;

    public ConsolePromptBuildingError(String pattern, Object... arguments) {
        this.innerException = new Exception(
                MessageFormat.format(pattern, arguments));
    }
}

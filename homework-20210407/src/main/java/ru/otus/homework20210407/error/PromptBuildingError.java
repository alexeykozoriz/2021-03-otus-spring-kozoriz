package ru.otus.homework20210407.error;

import java.text.MessageFormat;

/**
 * Ошибка создания подсказки к вопросу
 */
public class PromptBuildingError extends Exception {
    public PromptBuildingError(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
    }
}

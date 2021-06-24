package ru.otus.homework20210414.error;

/**
 * Ошибка чтения вопросов
 */
public class QuestionsReadingError extends Exception {
    public QuestionsReadingError(Throwable e) {
        super("Questions reading error", e);
    }
}

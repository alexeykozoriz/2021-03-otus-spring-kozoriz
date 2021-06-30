package ru.otus.homework20210421.service;

/**
 * Интерфейс сервисного класса для взаимодействия с пользователем
 */
public interface InteractionService {

    /**
     * Чтение строки
     *
     * @param prompt подсказка
     * @return не-пустая строка
     */
    String readString(String prompt);

    /**
     * Чтение целого числа из заданного интервала
     *
     * @param prompt  подсказка
     * @param minimum минимум
     * @param maximum максимум
     * @return целое число
     */
    long readNumberByInterval(String prompt, long minimum, long maximum);

    /**
     * Вывод строки
     *
     * @param text строка
     */
    void outputString(String text);
}

package ru.otus.homework20210407.service;

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
     * Чтение целого числа в интервале от 1 до заданного максимума
     *
     * @param prompt   подсказка
     * @param interval максимальное значение
     * @return целое число
     */
    int readIntByInterval(String prompt, int interval);

    /**
     * Вывод строки
     *
     * @param text строка
     */
    void outputString(String text);
}

package ru.otus.homework20210407.service;

/**
 * Интерфейс сервисного класса для проведения тестирования
 */
public interface RunnerService {
    /**
     * Запуск тестирования
     *
     * @throws Exception общее исключение
     */
    void runTesting() throws Exception;
}

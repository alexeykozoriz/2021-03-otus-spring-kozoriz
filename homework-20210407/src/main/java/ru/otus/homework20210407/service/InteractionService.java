package ru.otus.homework20210407.service;

/**
 * Интерфейс сервисного класса для взаимодействия с пользователем
 */
public interface InteractionService {

    /**
     * Запрос ответа на вопрос в произвольной форме
     *
     * @param question вопрос
     * @return не-пустая строка
     */
    String requestTextAnswer(String question);

    /**
     * Запрос ответа на вопрос в форме выбранной опции
     *
     * @param question    вопрос
     * @param optionsSize количество опций
     * @return целое число в пределах количества опций
     */
    Integer requestOptionNumber(String question, int optionsSize);

    /**
     * Отправка текстового сообщения
     *
     * @param text текст
     */
    void sendTextMessage(String text);
}

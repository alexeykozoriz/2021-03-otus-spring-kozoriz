package ru.otus.homework20210428.service;

import ru.otus.homework20210428.domain.BookComment;

/**
 * Интерфейс сервисного класса для работы с комментариями к книгам
 */
public interface BookCommentsService {

    /**
     * Создание или обновление комментария
     *
     * @param comment комментарий
     */
    void save(BookComment comment);

    /**
     * Удаление комментария
     *
     * @param id идентификатор
     */
    void delete(long id);
}

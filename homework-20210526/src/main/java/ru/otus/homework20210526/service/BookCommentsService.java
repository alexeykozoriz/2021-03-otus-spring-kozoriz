package ru.otus.homework20210526.service;


import ru.otus.homework20210526.domain.BookComment;

import java.util.List;

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
     * Получение всех комментариев к книге
     *
     * @param bookId идентификатор книги
     * @return список
     */
    List<BookComment> findByBookId(long bookId);

    /**
     * Удаление комментариев по книге
     *
     * @param bookId идентификатор книги
     */
    void deleteByBookId(long bookId);
}

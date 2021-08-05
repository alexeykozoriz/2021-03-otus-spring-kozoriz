package ru.otus.homework20210609.service;


import ru.otus.homework20210609.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервисного класса для работы с книгами
 */
public interface BookService {

    /**
     * Создание или обновление книги
     *
     * @param book книга
     */
    void save(Book book);

    /**
     * Удаление книги
     *
     * @param id идентификатор
     */
    void delete(long id);

    /**
     * Чтение книги
     *
     * @param id идентификатор
     * @return книга
     */
    Optional<Book> read(long id);

    /**
     * Чтение всех книг
     *
     * @return список
     */
    List<Book> read();
}

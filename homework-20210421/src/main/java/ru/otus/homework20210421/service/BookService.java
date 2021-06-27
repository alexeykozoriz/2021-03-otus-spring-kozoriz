package ru.otus.homework20210421.service;

import ru.otus.homework20210421.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервисного класса для работы с книгами
 */
public interface BookService {

    /**
     * Создание книги и возврат идентификатора
     *
     * @param book книга
     * @return целое число
     */
    long create(Book book);

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

    /**
     * Обновление книги
     *
     * @param book книга
     */
    void update(Book book);
}

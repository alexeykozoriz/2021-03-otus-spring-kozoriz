package ru.otus.homework20210421.dao;

import ru.otus.homework20210421.domain.Book;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Интерфейс хранилища книг
 */
public interface BookDao {
    int count();

    long insert(@NotNull Book book);

    void update(@NotNull Book book);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}

package ru.otus.homework20210428.repository;

import ru.otus.homework20210428.domain.Book;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища книг
 */
public interface BookRepository {

    Book save(@NotNull Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void deleteById(long id);
}

package ru.otus.homework20210607.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.homework20210607.domain.Book;

import java.util.List;

/**
 * Интерфейс хранилища книг
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

}

package ru.otus.homework20210517.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework20210517.domain.Book;

/**
 * Интерфейс хранилища книг
 */
public interface BookRepository extends CrudRepository<Book, Long> {

}

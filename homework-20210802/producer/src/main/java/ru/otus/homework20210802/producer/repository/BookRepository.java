package ru.otus.homework20210802.producer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.homework20210802.producer.domain.Book;

/**
 * Интерфейс хранилища книг
 */
@RepositoryRestResource(path = "book")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    Page<Book> findAll(Pageable pageable);
}

package ru.otus.projectwork.libraryservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.otus.projectwork.libraryservice.domain.Book;

/**
 * Интерфейс хранилища книг
 */
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    Page<Book> findAll(Pageable pageable);
}

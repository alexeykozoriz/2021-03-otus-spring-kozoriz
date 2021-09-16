package ru.otus.projectwork.libraryservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.projectwork.libraryservice.domain.Author;

import java.util.Optional;

/**
 * Интерфейс хранилища авторов
 */
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
    Optional<Author> findByFullNameEquals(@Param("fullName") String fullName);
}

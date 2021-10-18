package ru.otus.projectwork.libraryservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.projectwork.libraryservice.domain.Genre;

import java.util.Optional;

/**
 * Интерфейс хранилища жанров
 */
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {
    Optional<Genre> findByTitleEquals(@Param("title") String title);
}

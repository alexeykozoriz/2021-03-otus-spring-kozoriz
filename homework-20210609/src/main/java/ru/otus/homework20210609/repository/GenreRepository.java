package ru.otus.homework20210609.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework20210609.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища жанров
 */
public interface GenreRepository extends CrudRepository<Genre, Long> {

    Optional<Genre> findByTitleEquals(String title);

    List<Genre> findAll();
}

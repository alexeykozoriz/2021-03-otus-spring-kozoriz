package ru.otus.homework20210428.repository;

import ru.otus.homework20210428.domain.Genre;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища жанров
 */
public interface GenreRepository {

    Genre save(@NotNull Genre genre);

    Optional<Genre> findById(long id);

    Optional<Genre> findByTitle(String title);

    List<Genre> findAll();

    void deleteById(long id);
}

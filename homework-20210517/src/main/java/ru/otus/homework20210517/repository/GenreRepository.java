package ru.otus.homework20210517.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.homework20210517.domain.Genre;

import java.util.Optional;

/**
 * Интерфейс хранилища жанров
 */
public interface GenreRepository extends CrudRepository<Genre, Long> {

    @Query("select g from Genre g where g.title like :title")
    Optional<Genre> findByTitle(@Param("title") String title);
}

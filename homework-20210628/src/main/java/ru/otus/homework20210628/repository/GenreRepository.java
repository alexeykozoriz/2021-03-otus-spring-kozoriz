package ru.otus.homework20210628.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.homework20210628.domain.Genre;

/**
 * Интерфейс хранилища жанров
 */
@RepositoryRestResource(path = "genre")
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {

}

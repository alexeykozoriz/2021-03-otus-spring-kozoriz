package ru.otus.homework20210705.producer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.homework20210705.producer.domain.Author;

/**
 * Интерфейс хранилища авторов
 */
@RepositoryRestResource(path = "author")
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

}

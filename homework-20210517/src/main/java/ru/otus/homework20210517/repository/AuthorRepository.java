package ru.otus.homework20210517.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.homework20210517.domain.Author;

import java.util.Optional;

/**
 * Интерфейс хранилища авторов
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Query("select a from Author a where a.fullName like :fullName")
    Optional<Author> findByFullName(@Param("fullName") String fullName);
}

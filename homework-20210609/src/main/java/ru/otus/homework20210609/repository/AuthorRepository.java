package ru.otus.homework20210609.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework20210609.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища авторов
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByFullNameEquals(String fullName);

    List<Author> findAll();
}

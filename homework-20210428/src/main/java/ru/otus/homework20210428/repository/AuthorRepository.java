package ru.otus.homework20210428.repository;

import ru.otus.homework20210428.domain.Author;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища авторов
 */
public interface AuthorRepository {

    Author save(@NotNull Author author);

    Optional<Author> findById(long id);

    Optional<Author> findByFullName(String fullName);

    List<Author> findAll();

    void deleteById(long id);
}

package ru.otus.homework20210421.dao;

import ru.otus.homework20210421.domain.Author;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Интерфейс хранилища авторов
 */
public interface AuthorDao {
    int count();

    long insert(@NotNull Author author);

    void update(@NotNull Author author);

    Author getById(long id);

    List<Author> getByFullname(String pattern);

    List<Author> getAll();

    void deleteById(long id);
}

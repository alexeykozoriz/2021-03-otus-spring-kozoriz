package ru.otus.homework20210421.dao;

import ru.otus.homework20210421.domain.Genre;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Интерфейс хранилища жанров
 */
public interface GenreDao {
    int count();

    long insert(@NotNull Genre genre);

    void update(@NotNull Genre genre);

    Genre getById(long id);

    List<Genre> getByTitle(String pattern);

    List<Genre> getAll();

    void deleteById(long id);
}

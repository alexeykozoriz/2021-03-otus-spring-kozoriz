package ru.otus.homework20210421.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210421.dao.GenreDao;
import ru.otus.homework20210421.domain.Genre;

import java.util.List;

/**
 * Реализация по-умолчанию
 * Жанры не удаляются.
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public long create(Genre genre) {
        return genreDao.insert(genre);
    }

    @Override
    public List<Genre> read() {
        return genreDao.getAll();
    }

    @Override
    public List<Genre> read(String titlePattern) {
        return genreDao.getByTitle(titlePattern);
    }
}

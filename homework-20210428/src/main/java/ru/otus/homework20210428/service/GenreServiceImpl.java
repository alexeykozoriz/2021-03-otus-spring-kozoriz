package ru.otus.homework20210428.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210428.domain.Genre;
import ru.otus.homework20210428.repository.GenreRepository;

import java.util.List;

/**
 * Реализация по-умолчанию
 * Жанры не удаляются.
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Genre> read() {
        return genreRepository.findAll();
    }
}

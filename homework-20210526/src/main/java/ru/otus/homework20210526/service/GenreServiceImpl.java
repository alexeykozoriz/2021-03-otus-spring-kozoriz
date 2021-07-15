package ru.otus.homework20210526.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210526.domain.Genre;
import ru.otus.homework20210526.repository.GenreRepository;

import java.util.List;

/**
 * Реализация по-умолчанию
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

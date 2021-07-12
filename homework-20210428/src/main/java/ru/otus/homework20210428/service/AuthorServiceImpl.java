package ru.otus.homework20210428.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210428.domain.Author;
import ru.otus.homework20210428.repository.AuthorRepository;

import java.util.List;

/**
 * Реализация по-умолчанию
 * Авторы не удаляются.
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Author> read() {
        return authorRepository.findAll();
    }
}

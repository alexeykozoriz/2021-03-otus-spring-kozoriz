package ru.otus.homework20210526.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210526.domain.Author;
import ru.otus.homework20210526.repository.AuthorRepository;

import java.util.List;

/**
 * Реализация по-умолчанию
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

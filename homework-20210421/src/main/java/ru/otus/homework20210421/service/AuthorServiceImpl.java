package ru.otus.homework20210421.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210421.dao.AuthorDao;
import ru.otus.homework20210421.domain.Author;

import java.util.List;

/**
 * Реализация по-умолчанию
 * Авторы не удаляются.
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public long create(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public List<Author> read() {
        return authorDao.getAll();
    }

    @Override
    public List<Author> read(String fullnamePattern) {
        return authorDao.getByFullname(fullnamePattern);
    }
}

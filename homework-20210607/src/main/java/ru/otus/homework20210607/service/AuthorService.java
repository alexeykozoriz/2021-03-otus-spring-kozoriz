package ru.otus.homework20210607.service;


import ru.otus.homework20210607.domain.Author;

import java.util.List;

/**
 * Интерфейс сервисного класса для работы с авторами
 */
public interface AuthorService {
    /**
     * Чтение авторов
     *
     * @return список
     */
    List<Author> read();
}

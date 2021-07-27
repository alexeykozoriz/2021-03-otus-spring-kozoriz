package ru.otus.homework20210609.service;


import ru.otus.homework20210609.domain.Author;

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

package ru.otus.homework20210517.service;


import ru.otus.homework20210517.domain.Author;

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

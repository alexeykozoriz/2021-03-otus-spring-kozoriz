package ru.otus.homework20210526.service;


import ru.otus.homework20210526.domain.Genre;

import java.util.List;

/**
 * Интерфейс сервисного класса для работы с жанрами
 */
public interface GenreService {
    /**
     * Чтение жанров
     *
     * @return список
     */
    List<Genre> read();
}

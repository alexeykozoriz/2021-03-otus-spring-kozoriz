package ru.otus.homework20210517.service;


import ru.otus.homework20210517.domain.Genre;

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

package ru.otus.homework20210421.service;

import ru.otus.homework20210421.domain.Genre;

import java.util.List;

/**
 * Интерфейс сервисного класса для работы с жанрами
 */
public interface GenreService {

    /**
     * Создание жанра и возврат идентификатора
     *
     * @param genre жанр
     * @return целое число
     */
    long create(Genre genre);

    /**
     * Чтение жанров
     *
     * @return список
     */
    List<Genre> read();

    /**
     * Чтение авторов
     *
     * @param titlePattern образец названия
     * @return список
     */
    List<Genre> read(String titlePattern);
}

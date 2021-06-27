package ru.otus.homework20210421.service;

import ru.otus.homework20210421.domain.Author;

import java.util.List;

/**
 * Интерфейс сервисного класса для работы с авторами
 */
public interface AuthorService {

    /**
     * Создание автора и возврат идентификатора
     *
     * @param author автор
     * @return целое число
     */
    long create(Author author);

    /**
     * Чтение авторов
     *
     * @return список
     */
    List<Author> read();

    /**
     * Чтение авторов
     *
     * @param fullnamePattern образец имени
     * @return список
     */
    List<Author> read(String fullnamePattern);
}

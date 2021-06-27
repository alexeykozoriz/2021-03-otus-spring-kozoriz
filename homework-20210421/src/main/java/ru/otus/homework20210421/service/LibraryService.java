package ru.otus.homework20210421.service;

/**
 * Интерфейс сервисного класса библиотеки
 */
public interface LibraryService {
    /**
     * Добавление книги
     */
    void addBook();

    /**
     * Удаление книги
     */
    void removeBook();

    /**
     * Редактирование книги
     */
    void editBook();

    /**
     * Вывод книг
     */
    void printBooks();

    /**
     * Вывод авторов
     */
    void printAuthors();

    /**
     * Вывод жанров
     */
    void printGenres();
}

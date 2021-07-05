package ru.otus.homework20210428.service;

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
     *
     * @param bookId идентификатор книги
     */
    void editBook(long bookId);

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

    /**
     * Добавление комментария
     *
     * @param bookId идентификатор книги
     */
    void addComment(long bookId);

    /**
     * Вывод комментариев
     *
     * @param bookId идентификатор книги
     */
    void printComments(long bookId);
}

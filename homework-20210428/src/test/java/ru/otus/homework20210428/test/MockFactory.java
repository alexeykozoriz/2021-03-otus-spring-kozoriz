package ru.otus.homework20210428.test;

import ru.otus.homework20210428.domain.Author;
import ru.otus.homework20210428.domain.Book;
import ru.otus.homework20210428.domain.BookComment;
import ru.otus.homework20210428.domain.Genre;

/**
 * Фабрика тестовых объектов
 */
public final class MockFactory {
    public static final String JOHN_DOE = "John Doe";
    public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    public static final String THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG = "The quick brown fox jumps over the lazy dog";
    public static final int YEAR_2021 = 2021;
    public static final String TESTING_FICTION = "Testing fiction";

    /**
     * Создание тестового автора
     *
     * @return автор
     */
    public static Author createAuthor() {
        return Author.builder()
                .fullName(JOHN_DOE)
                .build();
    }

    /**
     * Создание тестового жанр
     *
     * @return жанр
     */
    public static Genre createGenre() {
        return Genre.builder()
                .title(TESTING_FICTION)
                .build();
    }

    /**
     * Создание тестового комментария
     *
     * @return комментарий
     */
    public static BookComment createBookComment() {
        return BookComment.builder()
                .text(LOREM_IPSUM)
                .author(JOHN_DOE)
                .build();
    }

    /**
     * Создание тестовой книги
     *
     * @return книга
     */
    public static Book createBook() {
        return Book.builder()
                .title(THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG)
                .publicationYear(YEAR_2021)
                .author(createAuthor())
                .genre(createGenre())
                .build();
    }
}

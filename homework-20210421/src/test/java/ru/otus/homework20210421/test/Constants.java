package ru.otus.homework20210421.test;

import ru.otus.homework20210421.domain.Author;
import ru.otus.homework20210421.domain.Book;
import ru.otus.homework20210421.domain.Genre;

/**
 * Класс с тестовыми константами
 */
public final class Constants {

    public static final long ID_MOCK = 3L;
    public static final String AUTHOR_FULLNAME_MOCK = "AUTHOR_MOCK";
    public static final Author AUTHOR_MOCK = Author.builder()
            .fullName(AUTHOR_FULLNAME_MOCK)
            .build();
    public static final Author AUTHOR_MOCK_WITH_ID = Author.builder()
            .id(ID_MOCK)
            .fullName(AUTHOR_FULLNAME_MOCK)
            .build();
    public static final String GENRE_TITLE_MOCK = "GENRE_MOCK";
    public static final Genre GENRE_MOCK = Genre.builder()
            .title(GENRE_TITLE_MOCK)
            .build();
    public static final Genre GENRE_MOCK_WITH_ID = Genre.builder()
            .id(ID_MOCK)
            .title(GENRE_TITLE_MOCK)
            .build();
    public static final Book BOOK_MOCK = Book.builder()
            .title("TITLE_MOCK")
            .publicationYear(2000)
            .author(AUTHOR_MOCK)
            .genre(GENRE_MOCK)
            .build();
    public static final Book BOOK_MOCK_WITH_ID = Book.builder()
            .id(ID_MOCK)
            .title("TITLE_MOCK")
            .publicationYear(2000)
            .author(AUTHOR_MOCK)
            .genre(GENRE_MOCK)
            .build();
    public static final Book BOOK_MOCK_WITH_IDS = Book.builder()
            .title("TITLE_MOCK")
            .publicationYear(2000)
            .author(Author.builder()
                    .id(1L)
                    .fullName(AUTHOR_FULLNAME_MOCK)
                    .build())
            .genre(Genre.builder()
                    .id(2L)
                    .title(GENRE_TITLE_MOCK)
                    .build())
            .build();

    private Constants() {

    }
}
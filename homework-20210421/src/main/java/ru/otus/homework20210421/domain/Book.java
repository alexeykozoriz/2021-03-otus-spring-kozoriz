package ru.otus.homework20210421.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Книга
 */
@Builder
@Data
public class Book {
    private Long id;
    private String title;
    private int publicationYear;
    private Author author;
    private Genre genre;
}

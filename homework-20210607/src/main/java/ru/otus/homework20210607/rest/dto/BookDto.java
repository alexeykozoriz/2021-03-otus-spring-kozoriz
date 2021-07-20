package ru.otus.homework20210607.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.homework20210607.domain.Book;

/**
 * Транспортный класс для книги
 */
@Data
@Builder
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private int publicationYear;
    private AuthorDto author;
    private GenreDto genre;

    /**
     * Фабричный метод для отображения в транспортный класс
     *
     * @param book книга
     * @return транспортный объект
     */
    public static BookDto fromBook(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .publicationYear(book.getPublicationYear())
                .author(AuthorDto.fromAuthor(book.getAuthor()))
                .genre(GenreDto.fromGenre(book.getGenre()))
                .build();
    }

    /**
     * Фабричный метод для отображения в сущность
     *
     * @param dto транспортный объект
     * @return книга
     */
    public static Book fromDto(BookDto dto) {
        final var authorDto = dto.getAuthor();
        final var author = authorDto != null ? AuthorDto.fromDto(authorDto) : null;
        final var genreDto = dto.getGenre();
        final var genre = genreDto != null ? GenreDto.fromDto(genreDto) : null;
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .publicationYear(dto.getPublicationYear())
                .author(author)
                .genre(genre)
                .build();
    }
}

package ru.otus.homework20210526.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.homework20210526.domain.BookComment;

/**
 * Транспортный класс для комментария
 */
@Data
@Builder
@AllArgsConstructor
public class BookCommentDto {
    private Long id;
    private String author;
    private String text;
    private BookDto book;

    /**
     * Фабричный метод для отображения в транспортный класс
     *
     * @param comment комментарий
     * @return транспортный объект
     */
    public static BookCommentDto toDto(BookComment comment) {
        return BookCommentDto.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .text(comment.getText())
                .book(BookDto.toDto(comment.getBook()))
                .build();
    }

    /**
     * Фабричный метод для отображения в сущность
     *
     * @param dto транспортный объект
     * @return комментарий
     */
    public static BookComment toDomainObject(BookCommentDto dto) {
        return BookComment.builder()
                .id(dto.getId())
                .author(dto.getAuthor())
                .text(dto.getText())
                .book(BookDto.toDomainObject(dto.getBook()))
                .build();
    }
}

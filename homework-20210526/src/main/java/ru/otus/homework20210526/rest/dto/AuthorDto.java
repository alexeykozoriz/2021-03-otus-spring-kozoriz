package ru.otus.homework20210526.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.homework20210526.domain.Author;

/**
 * Транспортный класс для автора
 */
@Data
@Builder
@AllArgsConstructor
public class AuthorDto {

    private Long id;
    private String fullName;

    /**
     * Фабричный метод для отображения в транспортный класс
     *
     * @param author автор
     * @return транспортный объект
     */
    public static AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .fullName(author.getFullName())
                .build();
    }

    /**
     * Фабричный метод для отображения в сущность
     *
     * @param dto транспортный объект
     * @return автор
     */
    public static Author toDomainObject(AuthorDto dto) {
        return Author.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .build();
    }
}

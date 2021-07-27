package ru.otus.homework20210609.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.homework20210609.domain.Genre;

/**
 * Транспортный класс для жанра
 */
@Data
@Builder
@AllArgsConstructor
public class GenreDto {
    private Long id;
    private String title;

    /**
     * Фабричный метод для отображения в транспортный класс
     *
     * @param genre жанр
     * @return транспортный объект
     */
    public static GenreDto fromGenre(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .title(genre.getTitle())
                .build();
    }

    /**
     * Фабричный метод для отображения в сущность
     *
     * @param dto транспортный объект
     * @return жанр
     */
    public static Genre fromDto(GenreDto dto) {
        return Genre.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .build();
    }
}

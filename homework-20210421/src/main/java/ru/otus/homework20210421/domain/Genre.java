package ru.otus.homework20210421.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Жанр
 */
@Builder
@Data
public class Genre {
    private Long id;
    private String title;
}

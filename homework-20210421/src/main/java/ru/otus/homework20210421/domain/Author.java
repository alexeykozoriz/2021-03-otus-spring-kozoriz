package ru.otus.homework20210421.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Автор
 */
@Builder
@Data
public class Author {
    private Long id;
    private String fullName;
}

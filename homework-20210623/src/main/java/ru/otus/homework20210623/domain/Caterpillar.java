package ru.otus.homework20210623.domain;

import lombok.Builder;
import lombok.Data;
import ru.otus.homework20210623.reference.Species;

/**
 * Гусеница
 */
@Data
@Builder
public class Caterpillar {
    private final long id;
    private final Species species;
}

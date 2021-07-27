package ru.otus.homework20210623.domain;

import lombok.Builder;
import lombok.Data;
import ru.otus.homework20210623.reference.Species;

/**
 * Бабочка
 */
@Data
@Builder
public class Butterfly {
    private final long id;
    private final Species species;
}
